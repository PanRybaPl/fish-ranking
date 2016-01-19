/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.panryba.mc.ranking;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author PanRyba.pl
 */
public class PointsCalculatorTest {
    
    @Test
    public void testCalculatePointsDiff() {
        int[][] tests = {
            {1000, 1028, 54, -27},
            {1000, 1015, 52, -26},
            {1000, 1014, 52, -26},
            {1000, 1007, 51, -25},
            {1000, 1006, 50, -25},
            {1000, 1000, 50, -25},
            {1000, 994, 50, -25},
            {1000, 993, 49, -25},
            {1000, 986, 48, -24},
            {1000, 1000 - 30 * 14, 10, -10}
        };
        
        for(int[] test : tests) {
            //PointsResult result = PointsCalculator.calculatePointsDiff(test[0], test[1]);
            //assertEquals(test[2], result.getKillerDiff());
            //assertEquals(test[3], result.getVictimDiff());
        }        
    }

    @Test
    public void testGetBaseVictimPointsDiff() {
    }
}