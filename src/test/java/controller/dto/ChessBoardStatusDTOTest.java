package controller.dto;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.ChessGame;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessBoardStatusDTOTest {

    /**
     * RNBQKBNR
     * PPPPPPPP
     * ........
     * ........
     * ........
     * ........
     * pppppppp
     * rnbqkbnr
     */
    @DisplayName("보드판을 문자열로 매핑한 뒤 DTO를 생성한다.")
    @Test
    void create() {
        ChessGame chessGame = new ChessGame();
        ChessBoardStatusDTO chessBoardStatusDTO = ChessBoardStatusDTO.from(chessGame);

        List<String> expectedBlackFirstRow = Arrays.asList("RNBQKBNR".split(""));
        List<String> expectedBlackSecondRow = Arrays.asList("PPPPPPPP".split(""));
        List<String> expectedBlankRow = Arrays.asList("........".split(""));
        List<String> expectedWhiteFirstRow = Arrays.asList("rnbqkbnr".split(""));
        List<String> expectedWhiteSecondRow = Arrays.asList("pppppppp".split(""));

        assertThat(chessBoardStatusDTO.boardStatus()).containsExactly(
                expectedBlackFirstRow,
                expectedBlackSecondRow,
                expectedBlankRow,
                expectedBlankRow,
                expectedBlankRow,
                expectedBlankRow,
                expectedWhiteSecondRow,
                expectedWhiteFirstRow
        );
    }
}