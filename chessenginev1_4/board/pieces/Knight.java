/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessenginev1_4.board.pieces;

import com.krlv.source.chessenginev1_4.board.ChessBoard;
import com.krlv.source.chessenginev1_4.moves.CaptureMove;
import com.krlv.source.chessenginev1_4.moves.Move;
import com.krlv.source.chessenginev1_4.moves.RegularMove;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class Knight extends Piece{
    
    private final int[] jumpIndecies = {17, 10, 6, -15, -17, -10, -6, 15};
    
    public Knight(int index, int color){
        setIndex(index);
        setType(2 * color);
    }
    
    @Override
    public ArrayList<Move> getValidMoves() {
        long start = System.nanoTime();
        ArrayList<Move> indecies = new ArrayList();
        int[] board = ChessBoard.getTypeBoard();
        
        for(Integer in : jumpIndecies){
            int target = getIndex() + in;
            if(indexIsInBoard(target)){
                if(Math.abs(getIndex() % 8 - target % 8) <= 2){
                    if(board[target] / getColor() < 0)
                        indecies.add(new CaptureMove(getIndex(), target));
                    else if(board[target] == 0)
                        indecies.add(new RegularMove(getIndex(), target));
                }
            }
        }
        
        System.out.println((System.nanoTime() - start) / 1000000000.0);
        
        return indecies;
        
    }
    
    @Override
    public ArrayList<Integer> getAttackIndecies() {
        ArrayList<Integer> indecies = new ArrayList();
        int[] board = ChessBoard.getTypeBoard();
        
        for(Integer in : jumpIndecies){
            int target = getIndex() + in;
            if(indexIsInBoard(target)){
                if(Math.abs(getIndex() % 8 - target % 8) <= 2){
                    indecies.add(target);
                }
            }
        }        
        return indecies;
        
    }
    
}
