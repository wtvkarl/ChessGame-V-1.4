/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.krlv.source.chessenginev1_4.moves;

import com.krlv.source.chessenginev1_4.board.ChessBoard;
import com.krlv.source.chessenginev1_4.board.pieces.Blank;
import com.krlv.source.chessenginev1_4.board.pieces.Piece;

/**
 *
 * @author kvill
 */
public class CaptureMove extends Move{
    
    public CaptureMove(int start, int target){
        startPiece = ChessBoard.getBoard()[start];
        startIndex = start;
        targetPiece = ChessBoard.getBoard()[target];
        targetIndex = target;
        type = MoveType.CAPTURE;
    }

    @Override
    public void execute() {
        ChessBoard.getBoard()[targetIndex] = startPiece;
        startPiece.setIndex(targetIndex);
        ChessBoard.getBoard()[startIndex] = new Blank(startIndex);
        if(targetPiece.isWhite())
            ChessBoard.getWhitePieces().remove(targetPiece);
        else{
            ChessBoard.getBlackPieces().remove(targetPiece);
        }
    }
    
}
