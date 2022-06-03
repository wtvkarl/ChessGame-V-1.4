/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessenginev1_4.board.pieces;

import com.krlv.source.chessenginev1_4.AttackMap;
import com.krlv.source.chessenginev1_4.board.ChessBoard;
import com.krlv.source.chessenginev1_4.board.FenReader;
import com.krlv.source.chessenginev1_4.moves.CaptureMove;
import com.krlv.source.chessenginev1_4.moves.CastlingMove;
import com.krlv.source.chessenginev1_4.moves.Move;
import com.krlv.source.chessenginev1_4.moves.RegularMove;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class King extends Piece{
        
    public King(int index, int color){
        setIndex(index);
        setType(6 * color);
    }
    
    @Override
    public ArrayList<Move> getValidMoves() {
        ArrayList<Move> indecies = new ArrayList();
        int[] board = ChessBoard.getTypeBoard();
        
        for(Integer in : ChessBoard.getDirectionOffsets()){
            int target = getIndex() + in;
            if(indexIsInBoard(target)){
                if(!AttackMap.isInEnemySquares(target)){
                    if(board[target] == 0){
                        indecies.add(new RegularMove(getIndex(), target));
                    }
                    else if(Integer.signum(board[target] / getColor()) == -1){
                        indecies.add(new CaptureMove(getIndex(), target));
                    }
                }
            }
        }
        
        indecies.addAll(getCastlingMoves());
        
        return indecies;
        
    }
    
    private ArrayList<Move> getCastlingMoves(){
        ArrayList<Move> moves = new ArrayList();
        
        String rights = (isWhite()) ? FenReader.getWhiteCastlingRights().toLowerCase() : 
                                      FenReader.getBlackCastlingRights().toLowerCase();
        if(rights.isEmpty())
            return moves;
        
        Piece kingsideRook = (isWhite()) ? ChessBoard.getBoard()[0] : ChessBoard.getBoard()[56];
        Piece queensideRook = (isWhite()) ? ChessBoard.getBoard()[7] : ChessBoard.getBoard()[63];
        
        //DISCLAIMER : for some reason the indecies are RIGHT to LEFT instead of left to right, might fix later
        
        if(rights.contains("k")){ //kingside castling
            if(kingsideRook.isRook()){ 
                int[] board = ChessBoard.getTypeBoard();
                if(getRow() == 0){ //white's side
                    if(board[1] == 0 && board[2] == 0){ //if the squares between king and rook are blank
                        if(!AttackMap.isInEnemySquares(1) && !AttackMap.isInEnemySquares(2)){
                            moves.add(new CastlingMove(getIndex(), kingsideRook.getIndex(), true));
                        }
                    }
                }
                else{
                    if(board[57] == 0 && board[58] == 0){ //if the squares between king and rook are blank
                        if(!AttackMap.isInEnemySquares(57) && !AttackMap.isInEnemySquares(58)){
                            moves.add(new CastlingMove(getIndex(), kingsideRook.getIndex(), true));
                        }
                    }
                }
            }
        }
        
        if(rights.contains("q")){ //queenside castling
            if(queensideRook.isRook()){ 
                int[] board = ChessBoard.getTypeBoard();
                if(getRow() == 0){ //white's side
                    if(board[5] == 0 && board[6] == 0){ //if the squares between king and rook are blank
                        if(!AttackMap.isInEnemySquares(5) && !AttackMap.isInEnemySquares(6)){
                            moves.add(new CastlingMove(getIndex(), queensideRook.getIndex(), true));
                        }
                    }
                }
                else{
                    if(board[61] == 0 && board[62] == 0){ //if the squares between king and rook are blank
                        if(!AttackMap.isInEnemySquares(61) && !AttackMap.isInEnemySquares(62)){
                            moves.add(new CastlingMove(getIndex(), queensideRook.getIndex(), true));
                        }
                    }
                }
            }
        }

        
        
        return moves;
    }
    
    public ArrayList<Integer> getAttackIndecies() {
        ArrayList<Integer> indecies = new ArrayList();
        
        for(int i = 0; i < 8; i++){
            int distToEdge = ChessBoard.getDistanceToEdge(getIndex(), i);
            for(int j = 1; j < distToEdge; j++){
                int target = getIndex() + ChessBoard.getDirectionOffsets()[i] * j;
                if(indexIsInBoard(target)){
                    indecies.add(target);
                    break;
                }
            }
        }
        
        return indecies;
        
    }
    
}
