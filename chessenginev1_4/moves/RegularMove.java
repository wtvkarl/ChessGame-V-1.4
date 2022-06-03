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
public class RegularMove extends Move{
    
    public RegularMove(int start, int target){
        startPiece = ChessBoard.getBoard()[start];
        startIndex = start;
        targetPiece = ChessBoard.getBoard()[target];
        targetIndex = target;
        type = MoveType.REGULAR;
    }

    @Override
    public void execute() {
        ChessBoard.getBoard()[targetIndex] = startPiece;
        startPiece.setIndex(targetIndex);
        ChessBoard.getBoard()[startIndex] = new Blank(startIndex);
    }
}
