package pl.panryba.mc.ranking.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.panryba.mc.ranking.PluginApi;
import pl.panryba.mc.ranking.entities.PlayerScore;

public class RankingCommand implements CommandExecutor {

    PluginApi api;
    
    public RankingCommand(PluginApi api) {
        this.api = api;
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if(!cmnd.getName().equalsIgnoreCase("ranking")) {
            return false;
        }
        
        if(strings.length > 1) {
            return false;
        }
        
        if(strings.length == 0)
        {
            if(!(cs instanceof Player)) {
                return false;
            }
        
            Player player = (Player)cs;
            PlayerScore rank = api.getPlayerRank(player);
            
            Integer score = rank.getScore();
            
            player.sendMessage("Twoj ranking: " + score.toString());
        }
        else if(strings.length == 1) {
            Player player = (Player)Bukkit.getPlayer(strings[0]);
            if(player == null)
            {
                cs.sendMessage("Nie znaleziono gracza o podanymi nicku");
                return true;
            }
            
            PlayerScore rank = api.getPlayerRank(player);
            Integer score = rank.getScore();
            
            cs.sendMessage(ChatColor.YELLOW + "Ranking " + player.getName() + ChatColor.GREEN + " - liczba punktow: " + ChatColor.YELLOW + score.toString());
        }
        return true;
    }
    
}
