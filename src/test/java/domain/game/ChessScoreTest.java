package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import domain.chessboard.ChessBoard;
import domain.chessboard.Row;
import domain.piece.BlackPawn;
import domain.piece.Color;
import domain.piece.WhitePawn;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessScoreTest {

    ChessScore chessScore = new ChessScore();

    @DisplayName("체스판의 초기 상태에서의 점수는 38점이다.")
    @Test
    void calculateScoreInitialBoard() {
        ChessBoard chessBoard = new ChessBoard();
        List<Row> board = chessBoard.getBoard();

        double expectedScore = 38.0d;
        double actualWhiteScore = chessScore.getScore(board, Color.WHITE);
        double actualBlackScore = chessScore.getScore(board, Color.BLACK);

        assertThat(actualWhiteScore).isEqualTo(expectedScore);
        assertThat(actualBlackScore).isEqualTo(expectedScore);
    }

    @DisplayName("같은 색상의 폰이 같은 세로줄에 있으면 0.5점으로 계산한다.")
    @Test
    void calculatePawnScore() {
        /* 아래의 형태의 board 를 만든다.
         Pp
         Pp
         */
        Row row = new Row(List.of(new BlackPawn(), new WhitePawn()));
        List<Row> board = List.of(row, row);

        double expectedScore = 1.0d;
        double actualWhitePawnScore = chessScore.getScore(board, Color.WHITE);
        double actualBlackPawnScore = chessScore.getScore(board, Color.BLACK);

        assertThat(actualWhitePawnScore).isEqualTo(expectedScore);
        assertThat(actualBlackPawnScore).isEqualTo(expectedScore);
    }
}
