/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessenginev1_4.moves;

import com.krlv.source.chessenginev1_4.board.pieces.Piece;

/**
 *
 * @author 3095515
 */
public abstract class Move {
    
    public Piece startPiece, targetPiece;
    public int startIndex, targetIndex;
    public MoveType type;
    
    public abstract void execute();
    
    public Piece getStartPiece(){
        return startPiece;
    }
    
    public Piece getTargetPiece(){
        return targetPiece;
    }
    
    public int getStartIndex(){
        return startIndex;
    }
    
    public int getTargetIndex(){
        return targetIndex;
    }
    
    public MoveType getType(){
        return type;
    }
    
    public int getRowDist(){
        return Math.abs((targetIndex/8) - (startIndex/8));
    }
    
    public int getColDist(){
        return Math.abs((targetIndex%8) - (startIndex%8));
    }
    
}
