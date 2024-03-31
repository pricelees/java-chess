package service;

import domain.chessboard.ChessBoard;
import domain.chessboard.MovingResult;
import domain.coordinate.Coordinate;
import domain.game.ChessGame;
import domain.piece.Blank;
import domain.piece.Color;
import domain.piece.base.ChessPiece;
import domain.piece.type.PieceType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import service.dao.BoardDao;
import service.dao.GameDao;
import service.dto.CoordinateDto;
import service.mapper.ChessPieceMapper;
import service.mapper.CoordinateMapper;

public class ChessGameService {

    private static final ChessGameService instance = new ChessGameService();
    private final BoardDao boardDao;
    private final GameDao gameDao;

    private ChessGameService() {
        boardDao = BoardDao.getInstance();
        gameDao = GameDao.getInstance();
    }

    public static ChessGameService getInstance() {
        return instance;
    }

    public ChessGame createInitialGame() {
        deleteGame();
        ChessGame chessGame = new ChessGame();
        List<CoordinateDto> boardDto = createBoardDto(chessGame.getCurrentBoard());
        boardDao.createBoard(boardDto);
        gameDao.createColor();

        return new ChessGame();
    }

    public ChessGame loadGame() {
        String movingColor = gameDao.loadColor();
        List<CoordinateDto> coordinateDtos = boardDao.loadBoard();
        ChessBoard chessBoard = loadBoard(coordinateDtos);

        return new ChessGame(chessBoard, Color.valueOf(movingColor));
    }

    public boolean move(ChessGame chessGame, String startCoordinate, String destinationCoordinate) {
        Coordinate start = CoordinateMapper.getCoordinate(startCoordinate);
        Coordinate destination = CoordinateMapper.getCoordinate(destinationCoordinate);
        MovingResult movingResult = chessGame.startTurn(start, destination);
        boolean isKingRemoved = movingResult.isKingRemoved();
        if (isKingRemoved) {
            deleteGame();
        }
        if (!isKingRemoved) {
            updateGame(start, destination, movingResult, chessGame.getMovablePieceColor());
        }
        return isKingRemoved;
    }

    public void updateGame(Coordinate start, Coordinate destination, MovingResult movingResult, Color movableColor) {
        boardDao.updateBoard(
                CoordinateDto.from(start, Blank.getInstance()),
                CoordinateDto.from(destination, movingResult.getMovingPiece())
        );
        gameDao.saveColor(movableColor.name());
    }

    public void deleteGame() {
        gameDao.delete();
        boardDao.delete();
    }

    public List<List<ChessPiece>> getPiecesInBoard(ChessGame chessGame) {
        Map<Coordinate, ChessPiece> board = chessGame.getCurrentBoard();
        List<List<ChessPiece>> result = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            result.add(getPiecesInRow(board, row));
        }
        return result;
    }

    private List<ChessPiece> getPiecesInRow(Map<Coordinate, ChessPiece> board, int row) {
        List<ChessPiece> result = new ArrayList<>();
        for (int column = 0; column < 8; column++) {
            result.add(board.get(new Coordinate(row, column)));
        }
        return result;
    }

    private ChessBoard loadBoard(List<CoordinateDto> coordinateDtos) {
        return new ChessBoard(coordinateDtos.stream()
                .collect(Collectors.toMap(coordinateDto -> CoordinateMapper.getCoordinate(coordinateDto.coordinate()),
                        coordinateDto -> ChessPieceMapper.getInstance(PieceType.valueOf(coordinateDto.pieceType()),
                                Color.valueOf(coordinateDto.pieceColor()))))
        );
    }

    private List<CoordinateDto> createBoardDto(Map<Coordinate, ChessPiece> board) {
        return board.entrySet().stream()
                .map(entry -> new CoordinateDto(
                        CoordinateMapper.getCoordinateName(entry.getKey()),
                        entry.getValue().getType().name(),
                        entry.getValue().getColor().name()
                ))
                .toList();
    }
}
