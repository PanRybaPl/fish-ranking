/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.panryba.mc.ranking;

import com.avaje.ebean.EbeanServer;
import java.util.List;
import org.bukkit.event.Listener;
import pl.panryba.mc.db.FishDbPlugin;
import pl.panryba.mc.ranking.commands.RankingCommand;
import pl.panryba.mc.ranking.entities.DuelState;
import pl.panryba.mc.ranking.entities.Kill;
import pl.panryba.mc.ranking.entities.PlayerScore;

/**
 *
 * @author PanRyba.pl
 */
public class Plugin extends FishDbPlugin {

    @Override
    public void onEnable() {
        EbeanServer database = getCustomDatabase();
        PluginApi api = new PluginApi(this, database);
        
        Listener listener = new RankingListener(api);
        getServer().getPluginManager().registerEvents(listener, this);
        
        getCommand("ranking").setExecutor(new RankingCommand(api));
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = super.getDatabaseClasses();
       
        list.add(PlayerScore.class);
        list.add(Kill.class);
        list.add(DuelState.class);
        
        return list;
    }    
}
