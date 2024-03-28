package view;

import controller.dto.ChessBoardStatusDTO;
import java.util.List;

public class OutputView {

    private static final String SCORE_FORMAT = "내 점수: %.1f, 상대 점수: %.1f.";

    public void printGameGuide() {
        System.out.print("""
                > 체스 게임을 시작합니다.
                > 게임 시작 : start
                > 게임 종료 : end
                > 게임 이동 : move source위치 target위치 - 예. move b2 b3
                """);
    }

    public void printBoard(ChessBoardStatusDTO chessBoardStatusDTO) {
        chessBoardStatusDTO.boardStatus().forEach(this::printRow);
    }

    private void printRow(List<String> row) {
        row.forEach(System.out::print);
        System.out.println();
    }

    public void printScore(double playerScore, double opponentScore) {
        System.out.println(formatScore(playerScore, opponentScore));
    }

    private String formatScore(double playerScore, double opponentScore) {
        return SCORE_FORMAT.formatted(playerScore, opponentScore);
    }

    public void printScoreResult(double playerScore, double opponentScore) {
        System.out.println(
                formatScore(playerScore, opponentScore) + " " + formatResult(playerScore, opponentScore)
        );
    }

    private String formatResult(double playerScore, double opponentScore) {
        if (playerScore > opponentScore) {
            return "승리.";
        }
        if (playerScore < opponentScore) {
            return "패배.";
        }
        return "무승부.";
    }
}
