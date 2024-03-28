package domain.game;

import domain.chessboard.ChessBoard;
import domain.chessboard.MovingResult;
import domain.chessboard.Row;
import domain.coordinate.Coordinate;
import domain.piece.Color;
import java.util.List;

public class ChessGame {

    private static final ChessScore chessScore = new ChessScore();
    private static final Color DEFAULT_START_COLOR = Color.WHITE;

    private final ChessBoard chessBoard;

    private Color movablePieceColor;

    public ChessGame() {
        this.chessBoard = new ChessBoard();
        movablePieceColor = DEFAULT_START_COLOR;
    }

    public MovingResult startTurn(Coordinate start, Coordinate destination) {
        boolean isKingRemoved = chessBoard.movePiece(start, destination, movablePieceColor);
        movablePieceColor = movablePieceColor.getOpponentColor();

        return new MovingResult(isKingRemoved, movablePieceColor);
    }

    public double calculatePlayerScore() {
        return chessScore.getScore(getCurrentBoard(), movablePieceColor);
    }

    public double calculateOpponentScore() {
        return chessScore.getScore(getCurrentBoard(), movablePieceColor.getOpponentColor());
    }

    public List<Row> getCurrentBoard() {
        return chessBoard.getBoard();
    }
}
