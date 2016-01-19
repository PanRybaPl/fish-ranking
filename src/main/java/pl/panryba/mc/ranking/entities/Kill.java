/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.panryba.mc.ranking.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author PanRyba.pl
 */

@Entity
@Table(name = "kills")
public class Kill {

    @Id
    @Column(name = "id")
    private Long id;
    
    @Column(name = "killer", nullable = false)
    private String killer;

    @Column(name = "victim", nullable = false)
    private String victim;
    
    @Column(name = "killer_points", nullable = true)
    private int killerPoints;
    
    @Column(name = "victim_points", nullable = true)
    private int victimPoints;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "occurred_at", nullable = false)
    private Date when;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long value) {
        this.id = value;
    }
    
    public Date getWhen() {
        return when;
    }
    
    public void setWhen(Date value) {
        this.when = value;
    }
    
    public String getVictim() {
        return victim;
    }
    
    public String getKiller() {
        return killer;
    }
    
    public void setVictim(String value) {
        victim = value;
    }
    
    public void setKiller(String value) {
        killer = value;
    }
    
    public void setKillerPoints(int points) {
        this.killerPoints = points;
    }
    
    public int getKillerPoints() {
        return this.killerPoints;
    }
    
    public void setVictimPoints(int points) {
        this.victimPoints = points;
    }
    
    public int getVictimPoints() {
        return this.victimPoints;
    }    
}
