/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessenginev1_4;

import com.krlv.source.chessenginev1_4.board.ChessBoard;
import com.krlv.source.chessenginev1_4.board.FenReader;
import com.krlv.source.chessenginev1_4.board.pieces.Piece;

/**
 *
 * @author 3095515
 */
public class AttackMap {
    
    private static int[] attackMap = new int[64];
    
    public static void updateAttackMap(){
        attackMap = new int[64];
        for(Piece piece : (FenReader.isWhitesTurn()) ? ChessBoard.getBlackPieces() : ChessBoard.getWhitePieces()){
            for(int index : piece.getAttackIndecies()){
                attackMap[index] = 1;
            }
        }
    }
    
    public static boolean isInEnemySquares(int targetIndex){  
        return attackMap[targetIndex] == 1;
    }
    
    public static void printAttackMap(){
        for(int row = 7; row >= 0; row--){
            for(int col = 7; col >= 0; col--){
                System.out.print(attackMap[row * 8 + col] + " ");
            }
            System.out.println();
        }
    }
    
}
