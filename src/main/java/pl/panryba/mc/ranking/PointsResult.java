/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.panryba.mc.ranking;

public class PointsResult {
    private final int killerDiff;
    private final int victimDiff;
    
    public PointsResult(int killerDiff, int victimDiff) {
        this.killerDiff = killerDiff;
        this.victimDiff = victimDiff;
    }
    
    public int getKillerDiff() {
        return this.killerDiff;
    }
    
    public int getVictimDiff() {
        return this.victimDiff;
    }
}
