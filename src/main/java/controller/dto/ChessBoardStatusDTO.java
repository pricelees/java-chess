package controller.dto;

import domain.chessboard.Row;
import domain.game.ChessGame;
import domain.piece.Color;
import domain.piece.base.ChessPiece;
import java.util.List;
import view.util.ChessPieceSymbol;

public record ChessBoardStatusDTO(List<List<String>> boardStatus) {

    public static ChessBoardStatusDTO from(ChessGame chessGame) {
        List<List<String>> boardStatus = chessGame.getCurrentBoard().stream()
                .map(ChessBoardStatusDTO::getRowStatus)
                .toList();

        return new ChessBoardStatusDTO(boardStatus);
    }

    private static List<String> getRowStatus(Row row) {
        return row.getPieces().stream()
                .map(ChessBoardStatusDTO::mapPieceToSymbol)
                .toList();
    }

    private static String mapPieceToSymbol(ChessPiece chessPiece) {
        ChessPieceSymbol chessPieceSymbol = ChessPieceSymbol.from(chessPiece);
        String symbol = chessPieceSymbol.getSymbol();

        if (chessPiece.hasSameColor(Color.WHITE)) {
            return symbol;
        }
        return symbol.toUpperCase();
    }
}
