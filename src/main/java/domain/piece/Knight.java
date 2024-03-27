package domain.piece;

import domain.coordinate.Coordinate;
import domain.direction.Direction;
import domain.direction.KnightDirection;
import domain.piece.base.ChessPieceBase;

public class Knight extends ChessPieceBase {

    private static final double KNIGHT_DEFAULT_SCORE = 2.5;

    public Knight(Color color) {
        super(color);
    }

    @Override
    public Direction getDirection(Coordinate start, Coordinate destination) {
        int rowDifference = start.calculateRowDifference(destination);
        int columnDifference = start.calculateColumnDifference(destination);

        return KnightDirection.getDirection(rowDifference, columnDifference);
    }

    @Override
    public double getDefaultScore() {
        return KNIGHT_DEFAULT_SCORE;
    }
}
