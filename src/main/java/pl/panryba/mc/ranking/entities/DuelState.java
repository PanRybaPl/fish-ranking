/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.panryba.mc.ranking.entities;

import com.avaje.ebean.EbeanServer;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author PanRyba.pl
 */
@Entity
@Table(name = "duel_states")
@UniqueConstraint(columnNames = {"first_player", "second_player"})
public class DuelState {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "first_player")
    private String firstPlayer;
    @Column(name = "second_player")
    private String secondPlayer;
    @Column(name = "winner")
    private String winner;
    
    @Column(name = "set_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date when;

    public String getId() {
        return this.id;
    }
    
    public void setId(String value) {
        this.id = value;
    }
    
    public String getFirstPlayer() {
        return firstPlayer;
    }

    public String getSecondPlayer() {
        return secondPlayer;
    }
    
    public void setFirstPlayer(String value) {
        this.firstPlayer = value;
    }
    
    public void setSecondPlayer(String value) {
        this.secondPlayer = value;
    }
    
    public void setPlayers(String playerA, String playerB) {
        DuelPlayers players = new DuelPlayers(playerA, playerB);
        
        this.id = players.getId();
        this.firstPlayer = players.getFirst();
        this.secondPlayer = players.getSecond();
    }

    public String getWinner() {
        return this.winner;
    }

    public void setWinner(String value) {
        this.winner = value;
    }
    
    public void setWhen(Date value) {
        this.when = value;
    }
    
    public Date getWhen() {
        return this.when;
    }

    public static DuelState getDuel(EbeanServer database, String playerA, String playerB) {
        String idToFind = makeId(playerA, playerB);
        DuelState state = (DuelState) database.find(DuelState.class, idToFind);

        return state;
    }

    private static String makeId(String playerA, String playerB) {
        DuelPlayers players = new DuelPlayers(playerA, playerB);
        return players.getId();
    }

    public static void setDuelWinner(EbeanServer database, String winner, String loser) {
        DuelState state = getDuel(database, winner, loser);
        
        if(state == null) {
            state = new DuelState();
            state.setPlayers(winner, loser);
        }

        state.setWinner(winner);
        state.setWhen(new Date());
        database.save(state);
    }
}
