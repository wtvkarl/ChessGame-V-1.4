/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessenginev1_4.board.pieces;

import com.krlv.source.chessenginev1_4.moves.Move;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public abstract class Piece {
    private int type;
    private int row, col, index, timesMoved;
    
    public abstract ArrayList<Move> getValidMoves();
    public abstract ArrayList<Integer> getAttackIndecies();
    
    public void setType(int newType){
        type = newType;
    }
    
    public int getType(){
        return type;
    }
    
    public int getIndex(){
        return index;
    }
    
    public int getRow(){
        return row;
    }
    
    public int getCol(){
        return col;
    }
    
    public int getTimesMoved(){
        return timesMoved;
    }
    
    public void incrementTimesMoved(){
        timesMoved++;
    }
    
    public void decrementTimesMoved(){
        timesMoved--;
    }
    
    public void setIndex(int newIndex){
        index = newIndex;
        row = newIndex / 8;
        col = newIndex % 8;
    }
    
    public int getColor(){
        return Integer.signum(type);
    }
    
    public String getColorString(){
        return (isWhite()) ? "w" : "b";
    }
    
    public boolean isPawn(){
        return Math.abs(type) == 1;
    }
    
    public boolean isKnight(){
        return Math.abs(type) == 2;
    }
    
    public boolean isBishop(){
        return Math.abs(type) == 3;
    }
    
    public boolean isRook(){
        return Math.abs(type) == 4;
    }
    
    public boolean isQueen(){
        return Math.abs(type) == 5;
    }
    
    public boolean isKing(){
        return Math.abs(type) == 6;
    }
    
    public boolean isBlank(){
        return type == 0;
    }
    
    public boolean isWhite(){
        return type > 0;
    }
    
    public boolean isBlack(){
        return type < 0;
    }
    
    public boolean isFriendly(Piece piece){
        return piece.getColor() == this.getColor();
    }
    
    public boolean isEnemy(Piece piece){
        return !piece.isBlank() && piece.getColor() / getColor() == -1;
    }
    
    public boolean isTheSameAs(Piece piece){
        return index == piece.getIndex();
    }
    
    public boolean indexIsInBoard(int in){
        return in >= 0 && in <= 63;
    }
    
}
