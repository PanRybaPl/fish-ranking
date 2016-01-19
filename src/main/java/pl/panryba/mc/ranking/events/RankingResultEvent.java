package pl.panryba.mc.ranking.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RankingResultEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player winner;
    private final Player loser;
    private final int winnerPoints;
    private final int loserPoints;

    public RankingResultEvent(Player winner, Player loser, int winnerPoints, final int loserPoints) {
        this.winner = winner;
        this.loser = loser;
        this.winnerPoints = winnerPoints;
        this.loserPoints = loserPoints;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getLoser() {
        return loser;
    }

    public int getWinnerPoints() {
        return winnerPoints;
    }

    public int getLoserPoints() {
        return loserPoints;
    }
}
