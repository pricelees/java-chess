package domain.piece;

import domain.coordinate.Coordinate;
import domain.direction.DiagonalDirection;
import domain.direction.Direction;
import domain.direction.StraightDirection;
import domain.piece.base.ChessPieceBase;

public class Queen extends ChessPieceBase {

    public Queen(Color color) {
        super(color);
    }

    @Override
    public Direction getDirection(Coordinate start, Coordinate destination) {
        int rowDifference = start.calculateRowDifference(destination);
        int columnDifference = start.calculateColumnDifference(destination);

        try {
            return DiagonalDirection.getDirection(rowDifference, columnDifference);
        } catch (IllegalArgumentException e) {
            return StraightDirection.getDirection(rowDifference, columnDifference);
        }
    }

    @Override
    public boolean cantMove(Coordinate start, Coordinate destination) {
        getDirection(start, destination);
        return false;
    }
}
