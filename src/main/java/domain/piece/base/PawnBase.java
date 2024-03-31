package domain.piece.base;

import domain.coordinate.Coordinate;
import domain.direction.DiagonalDirection;
import domain.direction.Direction;
import domain.direction.StraightDirection;
import domain.piece.Color;
import domain.piece.type.PieceType;

public abstract class PawnBase extends ChessPieceBase {

    private static final int PAWN_DEFAULT_SCORE = 1;

    public PawnBase(Color color) {
        super(PieceType.PAWN, color);
    }

    public abstract boolean isInitialPawn(Coordinate start);

    public abstract boolean canMove(Direction direction, int distance, boolean isInitialPawn);

    @Override
    public Direction getDirection(Coordinate start, Coordinate destination) {
        Direction direction = getPawnDirection(start, destination);
        int distance = start.calculateDistanceToDestination(direction, destination);

        if (canMove(direction, distance, isInitialPawn(start))) {
            return direction;
        }
        throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
    }

    @Override
    public double getDefaultScore() {
        return PAWN_DEFAULT_SCORE;
    }

    private Direction getPawnDirection(Coordinate start, Coordinate destination) {
        int rowDifference = start.calculateRowDifference(destination);
        int columnDifference = start.calculateColumnDifference(destination);

        if (Math.abs(rowDifference) == 1 && Math.abs(columnDifference) == 1) {
            return DiagonalDirection.getDirection(rowDifference, columnDifference);
        }

        return StraightDirection.getDirection(rowDifference, columnDifference);
    }

    public boolean isMovableDistance(int distance, boolean isInitialPawn) {
        return distance == 1 || (distance == 2 && isInitialPawn);
    }
}
