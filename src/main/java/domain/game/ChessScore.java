package domain.game;

import domain.chessboard.Row;
import domain.coordinate.Position;
import domain.piece.Color;
import domain.piece.base.ChessPiece;
import java.util.List;

public class ChessScore {

    public double getScore(List<Row> board, Color color) {
        return calculateScoreExceptPawn(board, color) + calculatePawnScore(board, color);
    }

    private double calculateScoreExceptPawn(List<Row> board, Color color) {
        return board.stream()
                .flatMap(row -> row.getPieces().stream())
                .filter(piece -> !piece.isPawn() && piece.hasSameColor(color))
                .mapToDouble(ChessPiece::getDefaultScore)
                .sum();
    }

    private double calculatePawnScore(List<Row> board, Color color) {
        double sum = 0D;
        for (int column = 0; column < board.size(); column++) {
            sum += calculatePawnScoreInColumn(board, color, column);
        }
        return sum;
    }

    private double calculatePawnScoreInColumn(List<Row> board, Color color, int column) {
        List<ChessPiece> pawnInColumn = board.stream()
                .map(row -> row.getPiece(Position.of(column)))
                .filter(piece -> piece.isPawn() && piece.hasSameColor(color))
                .toList();

        return sumByPawnCount(pawnInColumn);
    }

    /**
     * @param pawnInColumn 하나의 세로 줄에 있는 폰 리스트
     * @return 일단, 기본 점수인 1점으로 합계를 계산한 뒤, 폰이 해당 세로줄에 2개 이상 있으면 합계에 2를 나누어 0.5로 계산한다.
     */
    private double sumByPawnCount(List<ChessPiece> pawnInColumn) {
        double sum = pawnInColumn.stream()
                .mapToDouble(ChessPiece::getDefaultScore)
                .sum();

        if (sum > 1.0d) {
            return sum / 2;
        }
        return sum;
    }
}
