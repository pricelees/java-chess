package view;

import domain.coordinate.Coordinate;
import domain.piece.base.ChessPiece;
import java.util.Map;
import view.util.PieceTranslator;

public class OutputView {

    private static final int CHESS_BOARD_SIZE = 8;

    public void printGameGuide() {
        System.out.print("""
                > 체스 게임을 시작합니다.
                > 게임 시작 : start
                > 게임 종료 : end
                > 게임 이동 : move source위치 target위치 - 예. move b2 b3
                """);
    }

    public void printBoard(Map<Coordinate, ChessPiece> board) {
        for (int row = 0; row < CHESS_BOARD_SIZE; row++) {
            printRow(board, row);
            System.out.println();
        }
    }

    private void printRow(Map<Coordinate, ChessPiece> board, int row) {
        for (int column = 0; column < CHESS_BOARD_SIZE; column++) {
            Coordinate coordinate = new Coordinate(row, column);
            ChessPiece piece = board.get(coordinate);
            System.out.print(PieceTranslator.getName(piece));
        }
    }
}
