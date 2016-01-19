package pl.panryba.mc.ranking;

import java.util.Date;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class RankingListener implements Listener {
    private PluginApi api;
    
    public RankingListener(PluginApi api) {
        this.api = api;
    }
    
    @EventHandler
    public void playerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if(player == null) {
            return;
        }

        api.ensureHasRankingSet(player);
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = (Player)event.getEntity();
        if(victim == null) {
            return;
        }
        
        Player killer = victim.getKiller();
        if(killer == null) {
            api.updateSuicideRank(victim);
            return;
        }
        
        if(killer == victim) {
            return;
        }
        
        PointsResult points;
        if(api.shouldUpdateRank(killer, victim)) {
            points = api.updateHomicideRank(killer, victim);
        } else {
            points = new PointsResult(0, 0);
        }        

        int killerPoints = points.getKillerDiff();
        int victimPoints = points.getVictimDiff();

        api.registerKillAsync(killer.getName(), victim.getName(), new Date(), killerPoints, victimPoints);
        if(points.getKillerDiff() > 0) {
            event.setDeathMessage(ChatColor.GOLD + killer.getDisplayName() + ChatColor.GRAY + " pokonal " +
                    ChatColor.GOLD + victim.getDisplayName() + ChatColor.GRAY + " i otrzymal " + ChatColor.RED + points.getKillerDiff() + " pkt.");
        }
        else {
            event.setDeathMessage(ChatColor.GOLD + killer.getDisplayName() + ChatColor.GRAY + " pokonal " +
                    ChatColor.GOLD + victim.getDisplayName());
        }
        api.notifyRankingResult(killer, victim, killerPoints, victimPoints);
    }
}
