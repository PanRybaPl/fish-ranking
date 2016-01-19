/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.panryba.mc.ranking;

import com.avaje.ebean.EbeanServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import pl.panryba.mc.ranking.entities.DuelState;
import pl.panryba.mc.ranking.entities.Kill;
import pl.panryba.mc.ranking.entities.PlayerScore;
import pl.panryba.mc.ranking.events.RankingResultEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PluginApi {
    private final EbeanServer database;
    private Objective ratio;
    private final Plugin plugin;
    
    public PluginApi(Plugin plugin, EbeanServer database) {
        this.plugin = plugin;
        this.database = database;
        
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        
        this.ratio = board.getObjective("ratio");
        if(this.ratio == null) {
            this.ratio = board.registerNewObjective("ratio", "dummy");
        }
        
        ratio.setDisplaySlot(DisplaySlot.PLAYER_LIST);
    }
    
    public void registerKillAsync(final String killer, final String victim, final Date date, final int killerPoints, final int victimPoints) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, new Runnable() {

            @Override
            public void run() {
                Kill kill = new Kill();

                kill.setKiller(killer);
                kill.setVictim(victim);
                kill.setWhen(date);
                kill.setKillerPoints(killerPoints);
                kill.setVictimPoints(victimPoints);

                PluginApi.this.database.save(kill);
                
                DuelState.setDuelWinner(PluginApi.this.database, killer, victim);
            }
        });
    }

    public boolean killerNotAlreadyWon(Player killer, Player victim) {
        DuelState state = DuelState.getDuel(this.database, killer.getName(), victim.getName());

        if(state == null) {
            return true;
        }

        return !state.getWinner().equals(killer.getName());
    }

    public int updateSuicideRank(Player victim) {
        PlayerScore victimRank = getPlayerRank(victim);

        int victimDiff = PointsCalculator.getBaseVictimPointsDiff();
        int victimScore = Math.max(0, victimRank.getScore() + victimDiff);

        victimRank.setScore(victimScore);
        this.database.save(victimRank);
        updateScoreboard(victim, victimScore);

        return victimDiff;
    }

    public PointsResult updateHomicideRank(Player killer, Player victim) {
        PlayerScore victimRank = getPlayerRank(victim);        
        PlayerScore killerRank = getPlayerRank(killer);
        
        boolean alreadyWonDuel = !killerNotAlreadyWon(killer, victim);
        PointsResult pointsResult = PointsCalculator.calculatePointsDiff(killerRank.getScore(), victimRank.getScore(), alreadyWonDuel);
        
        int killerScore = Math.max(0, killerRank.getScore() + pointsResult.getKillerDiff());
        int victimScore = Math.max(0, victimRank.getScore() + pointsResult.getVictimDiff());
        
        killerRank.setScore(killerScore);
        victimRank.setScore(victimScore);
        
        List<PlayerScore> toUpdate = new ArrayList<>();
        toUpdate.add(killerRank);
        toUpdate.add(victimRank);
        
        this.database.save(toUpdate);
        
        updateScoreboard(killer, killerScore);
        updateScoreboard(victim, victimScore);
        
        return pointsResult;
    }

    public void ensureHasRankingSet(Player player) {
        PlayerScore rank = getPlayerRank(player);
        
        int score = rank.getScore();
        updateScoreboard(player, score);
    }

    public PlayerScore getPlayerRank(Player player) {
        PlayerScore result = PlayerScore.getPlayerRank(database, player.getName());
        
        if(result == null) {
            int score = getPlayerRatioScore(player);
            result = new PlayerScore(player.getName(), score);
            
            this.database.save(result);            
        }
        
        return result;
    }
    
    private void updateScoreboard(Player player, int scoreValue) {
        Score score = this.ratio.getScore(player);
        score.setScore(scoreValue);
    }
    
    private int getDefaultPlayerScore() {
        return 1000;
    }

    private int getPlayerRatioScore(Player player) {
        Score score = this.ratio.getScore(player);
        
        if(score == null || score.getScore() == 0) {
            return getDefaultPlayerScore();
        }

        return score.getScore();
    }    

    boolean shouldUpdateRank(Player killer, Player victim) {
        return !killer.getAddress().getAddress().equals(victim.getAddress().getAddress());
    }

    public void notifyRankingResult(Player killer, Player victim, int killerPoints, int victimPoints) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                RankingResultEvent event = new RankingResultEvent(killer, victim, killerPoints, victimPoints);
                PluginApi.this.plugin.getServer().getPluginManager().callEvent(event);
            }
        });
    }
}
