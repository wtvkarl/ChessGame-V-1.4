/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessenginev1_4.board;

import com.krlv.source.chessenginev1_4.board.pieces.Piece;
import com.krlv.source.chessenginev1_4.moves.Move;
import com.krlv.source.chessenginev1_4.moves.MoveType;

/**
 *
 * @author 3095515
 */
public class FenReader {
    private static String fen;
    private static String piecePositions, colorToMove, castlingRights,
                          enPassantSquare, halfMoveClock, fullMoveClock;
    
    private static int halfMoves, fullMoves;
    
    public FenReader(String startFen){
        fen = startFen;
        initializeFenParts(startFen);
    }
    
    public static void initializeFenParts(String newFen){
        fen = newFen;
        String[] fenParts = newFen.split(" ");
        piecePositions = fenParts[0];
        colorToMove = fenParts[1];
        castlingRights = fenParts[2];
        enPassantSquare = fenParts[3];
        halfMoveClock = fenParts[4];
        halfMoves = Integer.parseInt(fenParts[4]);
        fullMoveClock = fenParts[5];
        fullMoves = Integer.parseInt(fenParts[5]);
    }
    
    private static char getFenNotationByType(Piece piece){
        switch(Math.abs(piece.getType())){
            case 1 : return (piece.getType() > 0) ? 'P' : 'p'; 
            case 2 : return (piece.getType() > 0) ? 'N' : 'n'; 
            case 3 : return (piece.getType() > 0) ? 'B' : 'b';
            case 4 : return (piece.getType() > 0) ? 'R' : 'r';
            case 5 : return (piece.getType() > 0) ? 'Q' : 'q';
            case 6 : return (piece.getType() > 0) ? 'K' : 'k';
            default : return '*';
        }
    }
    
    public static void updatePositions(Piece[] newBoard){
        String str = "";
        for(int row = 7; row >= 0; row--){
            int blanks = 0;
            for(int col = 7; col >= 0; col--){
                Piece piece = newBoard[row * 8 + col];
                if(!piece.isBlank()){ 
                    if(blanks > 0){
                        str += blanks;
                        blanks = 0;
                    }
                    str += getFenNotationByType(piece);
                }
                else{
                    blanks++;
                }
            }
            if(blanks > 0)
                str += blanks;
            if(row != 0)
                str += "/";
        }
        piecePositions = str;
    }
    
    public static void switchTurn(){
        colorToMove = (isWhitesTurn()) ? "b" : "w";
    }
    
    public static void updateEnPassantSquare(String coor){
        enPassantSquare = coor;
    }
    
    public static boolean isWhitesTurn(){
        return colorToMove.equals("w");
    }
    
    public static boolean isBlacksTurn(){
        return colorToMove.equals("b");
    }
    
    public static String getFen(){
        String str = "";
        str += piecePositions + " ";
        str += colorToMove + " ";
        str += castlingRights + " ";
        str += enPassantSquare + " ";
        str += halfMoveClock + " ";
        str += fullMoveClock + " ";
        return str;
    }
    
    public static String getPiecePos(){
        return piecePositions;
    }
    
    public static String getColorToMove(){
        return colorToMove;
    }
    
    public static void updateCastlingRights(String rights){
        castlingRights = rights;
    }
    
    public static String getCastlingRights(){
        return castlingRights;
    }
    
    public static String getEnPassantSquare(){
        return enPassantSquare;
    }
    
    public static String getHalfMoves(){
        return halfMoveClock;
    }
    
    public static String getFullMoves(){
        return fullMoveClock;
    }
    
    public static void incrementHalfMoveClock(){
        halfMoves++;
        halfMoveClock = Integer.toString(halfMoves);
    }
    
    public static void incrementFullMoveClock(){
        fullMoves++;
        fullMoveClock = Integer.toString(fullMoves);
    }
    
    public static void updateMoveClocks(Move move){
        if(move.getStartPiece().isBlack())
            incrementFullMoveClock();
        if(move.getStartPiece().isPawn() || move.getType() == MoveType.CAPTURE){
            halfMoves = 0;
            halfMoveClock = Integer.toString(halfMoves);
        }
        else{
            incrementHalfMoveClock();
        }
    }
    
    public static String getWhiteCastlingRights(){
        String str = "";
        for(Character ch : castlingRights.toCharArray()){
            if(Character.isUpperCase(ch)){
                str += ch;
            }
        }
        return str;
    }
    
    public static String getBlackCastlingRights(){
        String str = "";
        for(Character ch : castlingRights.toCharArray()){
            if(Character.isLowerCase(ch)){
                str += ch;
            }
        }
        return str;
    }
    
    public static void printFen(){
        System.out.println(fen);
    }
    
}
