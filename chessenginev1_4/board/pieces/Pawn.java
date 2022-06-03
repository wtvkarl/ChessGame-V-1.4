/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessenginev1_4.board.pieces;

import com.krlv.source.chessenginev1_4.board.ChessBoard;
import com.krlv.source.chessenginev1_4.board.FenReader;
import com.krlv.source.chessenginev1_4.moves.CaptureMove;
import com.krlv.source.chessenginev1_4.moves.EnPassantMove;
import com.krlv.source.chessenginev1_4.moves.Move;
import com.krlv.source.chessenginev1_4.moves.RegularMove;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class Pawn extends Piece{
    
    public Pawn(int index, int color){
        setIndex(index);
        setType(1 * color);
    }

    @Override
    public ArrayList<Move> getValidMoves() {
        ArrayList<Move> indecies = new ArrayList();
        int[] board = ChessBoard.getTypeBoard();
        
        boolean firstTimeMoving = (isWhite()) ? getRow() == 1 : getRow() == 6;
        int spacesForward = (firstTimeMoving) ? 2 : 1;
        
        for(int i = 1; i <= spacesForward; i++){
            int target = getIndex() + (8 * getColor() * i);
            if(board[target] == 0)
                indecies.add(new RegularMove(getIndex(), target));
            
            if(i == 1){
                int leftCapture = target  + (-1);
                int rightCapture = target + 1;
                
                if(leftCapture / 8 == target / 8){ //same row
                    if(board[leftCapture] * getColor() < 0){ //enemy piece
                        indecies.add(new CaptureMove(getIndex(), leftCapture));
                    }
                    else if(ChessBoard.getCoordinate(leftCapture).equals(FenReader.getEnPassantSquare())){
                        indecies.add(new EnPassantMove(getIndex(), leftCapture));
                    }
                }
                if(rightCapture / 8 == target / 8){
                    if(board[rightCapture] * getColor() < 0){
                        indecies.add(new CaptureMove(getIndex(), rightCapture));
                    }
                    else if(ChessBoard.getCoordinate(rightCapture).equals(FenReader.getEnPassantSquare())){
                        indecies.add(new EnPassantMove(getIndex(), rightCapture));
                    }

                }
            }
        }        
        return indecies;
        
    }
    
    @Override
    public ArrayList<Integer> getAttackIndecies() {
        ArrayList<Integer> indecies = new ArrayList();
                
        int target = getIndex() + (8 * getColor());

        int leftCapture = target + (-1);
        int rightCapture = target + 1;

        if(leftCapture / 8 == target / 8){ //same row
            indecies.add(leftCapture);
        }
        
        if(rightCapture / 8 == target / 8){
            indecies.add(rightCapture);
        }
                        
        return indecies;
        
    }
    
}
