/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessenginev1_4.board.pieces;

import com.krlv.source.chessenginev1_4.board.ChessBoard;
import com.krlv.source.chessenginev1_4.moves.Move;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class Blank extends Piece{
    
    public Blank(int index){
        setIndex(index);
        setType(0);
    }
    
    @Override
    public ArrayList<Move> getValidMoves() {
        return new ArrayList();
    }

    @Override
    public ArrayList<Integer> getAttackIndecies() {
        return new ArrayList();
    }
    
}
