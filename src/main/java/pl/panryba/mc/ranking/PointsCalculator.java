package pl.panryba.mc.ranking;

public class PointsCalculator {
    public static PointsResult calculatePointsDiff(int killerScore, int victimScore, boolean alreadyWonDuel) {
        int killerBaseDiff = 50;
        
        // add/subtract 1 point per each 7 points of difference
        int killerMod = (int)Math.floor((victimScore - killerScore) / 7);
        
        int minimum = alreadyWonDuel ? 0 : 1;
        
        int killerDiff = Math.max(minimum, killerBaseDiff + killerMod);
        int victimDiff = -killerDiff;
        
        return new PointsResult(killerDiff, victimDiff);
    }
    
    public static int getBaseVictimPointsDiff() {
        return -25;
    }
}
