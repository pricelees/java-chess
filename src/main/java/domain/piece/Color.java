package domain.piece;

public enum Color {

    BLACK,
    WHITE;

    public Color getOpponentColor() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }
}
