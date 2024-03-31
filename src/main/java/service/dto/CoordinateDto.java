package service.dto;

import domain.coordinate.Coordinate;
import domain.piece.base.ChessPiece;
import service.mapper.CoordinateMapper;

public record CoordinateDto(String coordinate, String pieceType, String pieceColor) {

    public static CoordinateDto from(Coordinate coordinate, ChessPiece chessPiece) {
        String coordinateName = CoordinateMapper.getCoordinateName(coordinate);
        String pieceType = chessPiece.getType().name();
        String pieceColor = chessPiece.getColor().name();

        return new CoordinateDto(coordinateName, pieceType, pieceColor);
    }
}
