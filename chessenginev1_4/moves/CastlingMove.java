/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessenginev1_4.moves;

import com.krlv.source.chessenginev1_4.board.ChessBoard;
import com.krlv.source.chessenginev1_4.board.pieces.Blank;

/**
 *
 * @author 3095515
 */
public class CastlingMove extends Move{
    
    private int newRookIndex, oldRookIndex;
    
    public CastlingMove(int start, int target, boolean kingside){
        startPiece = ChessBoard.getBoard()[start];
        startIndex = start;
        oldRookIndex = target;
        targetPiece = ChessBoard.getBoard()[target];
        targetIndex = (kingside) ? start - 2 : start + 2;
        newRookIndex = (kingside) ? targetIndex + 1 : targetIndex - 1;
        type = MoveType.CASTLING;
    }

    @Override
    public void execute() {
        ChessBoard.getBoard()[newRookIndex] = targetPiece;
        ChessBoard.getBoard()[targetIndex] = startPiece;
        ChessBoard.getBoard()[targetPiece.getIndex()] = new Blank(oldRookIndex);
        ChessBoard.getBoard()[startPiece.getIndex()] = new Blank(startPiece.getIndex());
        targetPiece.setIndex(newRookIndex);
        startPiece.setIndex(targetIndex);
    }
    
}
