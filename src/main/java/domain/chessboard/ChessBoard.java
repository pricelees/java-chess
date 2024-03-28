package domain.chessboard;

import domain.coordinate.Coordinate;
import domain.coordinate.Position;
import domain.direction.Direction;
import domain.piece.Blank;
import domain.piece.Color;
import domain.piece.King;
import domain.piece.base.ChessPiece;
import java.util.List;

public class ChessBoard {

    private final List<Row> board;

    public ChessBoard() {
        this.board = ChessBoardInitializer.createInitialBoard();
    }

    /**
     * @param start 이동할 말의 위치
     * @param destination 말을 이동시키고자 하는 위치
     * @param currentTurnColor 현재 이동이 가능한 말의 색상
     * @return 이동이 불가능하면 예외, 상대의 King을 잡고 이동하면 true, 이외의 이동에 대해선 false
     */
    public boolean movePiece(Coordinate start, Coordinate destination, Color currentTurnColor) {
        Direction direction = getMovingPiece(start, currentTurnColor).getDirection(start, destination);
        validateDestination(destination, currentTurnColor);
        validatePath(direction, start, destination);

        return move(start, destination);
    }

    private ChessPiece getMovingPiece(Coordinate start, Color currentTurnColor) {
        ChessPiece piece = getPiece(start);

        if (!piece.hasSameColor(currentTurnColor)) {
            throw new IllegalArgumentException("색상이 다른 말을 움직일 수 없습니다.");
        }
        return piece;
    }

    private void validateDestination(Coordinate destination, Color currentTurnColor) {
        if (isEmptyCoordinate(destination)) {
            return;
        }
        if (getPiece(destination).hasSameColor(currentTurnColor)) {
            throw new IllegalArgumentException("같은 색의 말이 있는 곳으로는 이동할 수 없습니다.");
        }
    }

    private void validatePath(Direction direction, Coordinate start, Coordinate destination) {
        Coordinate current = start.moveOneStep(direction);

        while (!current.equals(destination)) {
            validatePieceExist(current);
            current = current.moveOneStep(direction);
        }
    }

    private void validatePieceExist(Coordinate current) {
        if (!isEmptyCoordinate(current)) {
            throw new IllegalArgumentException("이동 경로에 말이 존재합니다.");
        }
    }

    private boolean isEmptyCoordinate(Coordinate coordinate) {
        return getPiece(coordinate) instanceof Blank;
    }

    private boolean move(Coordinate start, Coordinate destination) {
        ChessPiece movingPiece = getPiece(start);
        ChessPiece removedPiece = getPiece(destination);

        replacePiece(start, Blank.getInstance());
        replacePiece(destination, movingPiece);

        return removedPiece instanceof King;
    }

    private void replacePiece(Coordinate coordinate, ChessPiece chessPiece) {
        Row row = getRow(coordinate.getRow());
        row.replace(coordinate.getColumn(), chessPiece);
    }

    private ChessPiece getPiece(Coordinate coordinate) {
        Row row = getRow(coordinate.getRow());

        return row.getPiece(coordinate.getColumn());
    }

    private Row getRow(Position row) {
        return board.get(row.getValue());
    }

    public List<Row> getBoard() {
        return List.copyOf(board);
    }
}
