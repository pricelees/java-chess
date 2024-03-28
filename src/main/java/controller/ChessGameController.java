package controller;

import controller.dto.ChessBoardStatusDTO;
import domain.chessboard.MovingResult;
import domain.coordinate.Coordinate;
import domain.game.ChessGame;
import view.InputView;
import view.OutputView;
import view.dto.Commands;
import view.dto.CoordinateRequest;
import view.util.Command;

public class ChessGameController {

    private final InputView inputView;
    private final OutputView outputView;

    public ChessGameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printGameGuide();

        ChessGame chessGame = initializeGame();
        printCurrentBoardStatus(chessGame);
        start(chessGame);
    }

    private ChessGame initializeGame() {
        Commands commands = inputView.receiveCommands();

        if (Command.START != commands.command()) {
            throw new IllegalArgumentException("게임을 먼저 시작하세요.");
        }
        return new ChessGame();
    }

    private void start(ChessGame chessGame) {
        boolean isPlaying;

        do {
            isPlaying = playGame(chessGame);
        } while (isPlaying);
    }

    private boolean playGame(ChessGame chessGame) {
        Commands commands = inputView.receiveCommands();

        if (isCommandMove(commands)) {
            return move(chessGame, commands);
        }
        if (isCommandStatus(commands.command())) {
            printScore(chessGame);
            return true;
        }
        printResultByComparingScore(chessGame);
        return false;
    }

    private boolean isCommandMove(Commands commands) {
        if (commands.command() == Command.START) {
            throw new IllegalArgumentException("이미 시작한 상태 입니다.");
        }

        return commands.command() == Command.MOVE;
    }

    private boolean isCommandStatus(Command command) {
        return command == Command.STATUS;
    }

    private boolean move(ChessGame chessGame, Commands commands) {
        Coordinate start = createCoordinate(commands.startCoordinate());
        Coordinate destination = createCoordinate(commands.destinationCoordinate());
        MovingResult movingResult = chessGame.startTurn(start, destination);
        printCurrentBoardStatus(chessGame);

        boolean isNextTurnAvailable = movingResult.isNextTurnAvailable();
        if (!isNextTurnAvailable) {
            outputView.printMessageWhenRemoveOpponentKing();
        }

        return isNextTurnAvailable;
    }

    private Coordinate createCoordinate(CoordinateRequest coordinateRequest) {
        return new Coordinate(coordinateRequest.row(), coordinateRequest.column());
    }

    private void printCurrentBoardStatus(ChessGame chessGame) {
        outputView.printBoard(ChessBoardStatusDTO.from(chessGame));
    }

    private void printScore(ChessGame chessGame) {
        outputView.printScore(chessGame.calculatePlayerScore(), chessGame.calculateOpponentScore());
    }

    private void printResultByComparingScore(ChessGame chessGame) {
        outputView.printScoreResult(chessGame.calculatePlayerScore(), chessGame.calculateOpponentScore());
    }
}
