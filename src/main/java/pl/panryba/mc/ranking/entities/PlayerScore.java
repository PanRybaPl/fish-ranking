/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.panryba.mc.ranking.entities;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Query;
import com.avaje.ebean.Transaction;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author PanRyba.pl
 */

@Entity
@Table(name = "player_scores")
public class PlayerScore {

    @Id
    @Column(name = "player")
    private String player;
    
    @Column(name = "score", nullable = false)
    private int score;
    
    public static PlayerScore getPlayerRank(EbeanServer server, String player) {
        PlayerScore rank = server.find(PlayerScore.class, player);
        return rank;
    }
/*   
    select * from (select player_scores.*, @curRow := @curRow + 1 as position from player_scores join (select @curRow := 0) r order by score desc, last_updated_at) sub where sub.player = '" + player + "'"
*/    
    public PlayerScore() {
    }
    
    public PlayerScore(String player, int score) {
        this.player = player;
        this.score = score;
    }
    
    public String getPlayer() {
        return this.player;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public void setPlayer(String player) {
        this.player = player;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
}
