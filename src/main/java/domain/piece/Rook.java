package domain.piece;

import domain.coordinate.Coordinate;
import domain.direction.Direction;
import domain.direction.StraightDirection;
import domain.piece.base.ChessPieceBase;

public class Rook extends ChessPieceBase {

    private static final double ROOK_DEFAULT_SCORE = 2.5;

    public Rook(Color color) {
        super(color);
    }

    @Override
    public Direction getDirection(Coordinate start, Coordinate destination) {
        int rowDifference = start.calculateRowDifference(destination);
        int columnDifference = start.calculateColumnDifference(destination);

        return StraightDirection.getDirection(rowDifference, columnDifference);
    }

    @Override
    public double getDefaultScore() {
        return ROOK_DEFAULT_SCORE;
    }
}
