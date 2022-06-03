/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessenginev1_4.moves;

import com.krlv.source.chessenginev1_4.board.ChessBoard;
import com.krlv.source.chessenginev1_4.board.FenReader;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class MoveHandler {

    public static Move getMove(ArrayList<Move> validMoves, int targetPieceIndex) {
        for(Move move : validMoves){
            if(move.getTargetIndex() == targetPieceIndex)
                return move;
        }
        return null;
    }
    
    public static String updateEnPassantSquareCoordinate(Move move){
        if(!move.getStartPiece().isPawn())
            return "-";
        if(move.getType() != MoveType.REGULAR)
            return "-";
        if(move.getRowDist() != 2)
            return "-";
        
        int indexBehindPawn = move.getStartPiece().isWhite() ?
                move.getStartIndex() + 8 :
                move.getStartIndex() - 8 ;     
        
        return ChessBoard.getCoordinate(indexBehindPawn);
    }
    
    public static String updateCastlingRights(Move move){
        String castlingRights = FenReader.getCastlingRights();
        if(move.getStartPiece().isKing()){
            if(move.getStartPiece().isWhite()){
                castlingRights = castlingRights.replace("K", "");
                castlingRights = castlingRights.replace("Q", "");
            }
            else if(move.getStartPiece().isBlack()){
                castlingRights = castlingRights.replace("k", "");
                castlingRights = castlingRights.replace("q", "");
            }
        }
        else if(move.getStartPiece().isRook()){
            if(move.getStartPiece().isWhite()){
                if(move.getStartIndex() == 7) //queenside
                    castlingRights = castlingRights.replace("Q", "");
                else if(move.getStartIndex() == 0) //kingside
                    castlingRights = castlingRights.replace("K", "");
            }
            else if(move.getStartPiece().isBlack()){
                if(move.getStartIndex() == 63) //queenside
                    castlingRights = castlingRights.replace("q", "");
                else if(move.getStartIndex() == 56) //kingside
                    castlingRights = castlingRights.replace("k", "");
            }
        }
        return castlingRights;
    }
    
    
    
}
