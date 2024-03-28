package domain.chessboard;

import domain.piece.Color;

public class MovingResult {

    private final boolean isKingRemoved;
    private final Color removedKingColor;

    public MovingResult(boolean isKingRemoved, Color removedKingColor) {
        this.isKingRemoved = isKingRemoved;
        this.removedKingColor = removedKingColor;
    }

    public boolean isNextTurnAvailable() {
        return !isKingRemoved;
    }

    public Color getRemovedKingColor() {
        return removedKingColor;
    }
}
