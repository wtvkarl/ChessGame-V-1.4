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
public class Queen extends Piece{
    
    private int dirStart = 0;
    
    public Queen(int index, int color){
        setIndex(index);
        setType(5 * color);
    }
    
    @Override
    public ArrayList<Move> getValidMoves() {
        long start = System.nanoTime();
        ArrayList<Move> indecies = new ArrayList();
        int[] board = ChessBoard.getTypeBoard();
        
        for(int i = dirStart; i < 8; i++){
            int distToEdge = ChessBoard.getDistanceToEdge(getIndex(), i);
            for(int j = 1; j <= distToEdge; j++){
                int target = getIndex() + ChessBoard.getDirectionOffsets()[i] * j;
                if(indexIsInBoard(target)){
                    if(board[target] == 0){
                        indecies.add(new RegularMove(getIndex(), target));
                    }
                    else{ 
                        if(Integer.signum(board[target] * getColor()) == -1){
                            indecies.add(new CaptureMove(getIndex(), target));
                        }      
                        break;
                    }
                }
            }
        }
        
        System.out.println((System.nanoTime() - start) / 1_000_000_000.0);

        return indecies;
        
    }
    
    public ArrayList<Integer> getAttackIndecies() {
        ArrayList<Integer> indecies = new ArrayList();
        int[] board = ChessBoard.getTypeBoard();
        
        for(int i = dirStart; i < 8; i++){
            int distToEdge = ChessBoard.getDistanceToEdge(getIndex(), i);
            for(int j = 1; j <= distToEdge; j++){
                int target = getIndex() + ChessBoard.getDirectionOffsets()[i] * j;
                if(indexIsInBoard(target)){
                    indecies.add(target);
                    if(board[target] != 0 && Math.abs(board[target]) != 6)
                        break;
                }
            }
        }

        return indecies;
        
    }
    
}
