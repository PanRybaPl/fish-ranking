/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.panryba.mc.ranking.entities;

/**
 *
 * @author PanRyba.pl
 */
class DuelPlayers {

    private String first;
    private String second;

    public DuelPlayers(String playerA, String playerB) {
        if (playerA.compareTo(playerB) > 0) {
            first = playerB;
            second = playerA;
        } else {
            first = playerA;
            second = playerB;
        }
    }

    public String getId() {
        return first + " " + second;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }
}
