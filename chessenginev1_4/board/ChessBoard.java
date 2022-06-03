/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessenginev1_4.board;

import com.krlv.source.chessenginev1_4.AttackMap;
import com.krlv.source.chessenginev1_4.InputHandler;
import com.krlv.source.chessenginev1_4.board.pieces.Bishop;
import com.krlv.source.chessenginev1_4.board.pieces.Blank;
import com.krlv.source.chessenginev1_4.board.pieces.King;
import com.krlv.source.chessenginev1_4.board.pieces.Knight;
import com.krlv.source.chessenginev1_4.board.pieces.Pawn;
import com.krlv.source.chessenginev1_4.board.pieces.Piece;
import com.krlv.source.chessenginev1_4.board.pieces.Queen;
import com.krlv.source.chessenginev1_4.board.pieces.Rook;
import com.krlv.source.chessenginev1_4.gfx.ImageLoader;
import com.krlv.source.chessenginev1_4.moves.Move;
import com.krlv.source.chessenginev1_4.moves.MoveHandler;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class ChessBoard {
    
    private static final String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private static final int squareSize = 80;
    //N, S, E, W, NE, SW, NW, SE (relative to white's perspective)
    private static final int[] directionOffsets = {8, -8, 1, -1, 9, -9, 7, -7};
    private static int[][] distancesToEdge;
    
    private FenReader fenReader;
    private static Piece[] board;
    private static int[] typeBoard;
    private static String[] coordinateBoard;
    
    private int draggedPieceIndex = -1;
    private Piece draggedPiece;
    private ArrayList<Move> validMoves = new ArrayList();
    private static Move previousMove;
    
    private static ArrayList<Piece> whitePieces, blackPieces;
    
    public ChessBoard(){
        fenReader = new FenReader(startFen);
        whitePieces = new ArrayList();
        blackPieces = new ArrayList();
        board = new Piece[64];
        typeBoard = new int[64];
        coordinateBoard = new String[64];
        initializeCoordinates();
        initializeBoard();
        initializeEdgeDistancesArray();
        AttackMap.updateAttackMap();
        System.out.println(this);
    }
    
    private void initializeCoordinates(){
        char file = 104;
        for(int row = 0; row < 8; row++){
            for(int col = 7; col >= 0; col--){
                coordinateBoard[col * 8 + row] = file + "" + (col + 1);
            }
            file--;
        }
    }

    // instead of using objects to represent pieces on a board, first use integers, much faster
    // pieces go from 1-6, white pieces are positive, black pieces negative
    // pawn = 1, knight = 2, bishop = 3, rook = 4, queen = 5, king = 6
    private void initializeBoard() {
        String piecePositions = fenReader.getPiecePos();
        int index = 63;
        for(Character ch : piecePositions.toCharArray()){
            if(Character.isDigit(ch)){
                int blanks =  Character.getNumericValue(ch);
                while(blanks > 0){
                    board[index] = new Blank(index);
                    blanks--;
                    index--;
                }
                continue;
            }
            else if(ch == '/'){
                continue;
            }
            else if(Character.isUpperCase(ch)){
                switch(ch){
                    case 'P' -> board[index] = new Pawn(index, 1);
                    case 'N' -> board[index] = new Knight(index, 1);
                    case 'B' -> board[index] = new Bishop(index, 1);
                    case 'R' -> board[index] = new Rook(index, 1);
                    case 'Q' -> board[index] = new Queen(index, 1);
                    case 'K' -> board[index] = new King(index, 1);
                }
            }
            else{
                switch(ch){
                    case 'p' -> board[index] = new Pawn(index, -1);
                    case 'n' -> board[index] = new Knight(index, -1);
                    case 'b' -> board[index] = new Bishop(index, -1);
                    case 'r' -> board[index] = new Rook(index, -1);
                    case 'q' -> board[index] = new Queen(index, -1);
                    case 'k' -> board[index] = new King(index, -1);
                }
            }
            
            if(board[index].isWhite()){
                whitePieces.add(board[index]);
            }
            else if(board[index].isBlack()){
                blackPieces.add(board[index]);
            }
            
            index--;
        }
        
        updateTypeBoard();
    }
    
    private void initializeEdgeDistancesArray(){
        distancesToEdge = new int[64][8];
        for(int i = 0; i < 64; i++){
            int row = i / 8;
            int col = i % 8;
            
            int distN = 7 - row;
            int distS = row;
            int distW = col;
            int distE = 7 - col;
            int distNE = Math.min(distN, distE);
            int distNW = Math.min(distN, distW);
            int distSE = Math.min(distS, distE);
            int distSW = Math.min(distS, distW);
            
            int[] distances = {
                distN,
                distS,
                distE,
                distW,
                distNE,
                distSW,
                distNW,
                distSE
            };
            
            distancesToEdge[i] = distances;
        }
    }
    
    //-------BOARD ACCESSOR METHODS-------
    public static Piece[] getBoard(){
        return board;
    }
    
    public static int[] getTypeBoard(){
        return typeBoard;
    }
    
    public static String[] getCoordinateBoard(){
        return coordinateBoard;
    }
    
    public static String getCoordinate(int index){
        return coordinateBoard[index];
    }
    
    private static void updateTypeBoard(){
        typeBoard = new int[64];
        for(int row = 7; row >= 0; row--){
            for(int col = 7; col >= 0; col--){
                typeBoard[row * 8 + col] = board[row * 8 + col].getType();
            }
        }
    }    
    public static int getDistanceToEdge(int pieceIndex, int direction){
        return distancesToEdge[pieceIndex][direction];
    }
    
    public static int[] getDirectionOffsets(){
        return directionOffsets;
    }
    
    public static ArrayList<Piece> getWhitePieces(){
        return whitePieces;
    }
    
    public static ArrayList<Piece> getBlackPieces(){
        return blackPieces;
    }
        
    //-------HELPER METHODS--------
    
    private Piece getPiece(int mouseX, int mouseY){
        int col = fenReader.isWhitesTurn() ? 
                7 - ((mouseX - mouseX % squareSize) / squareSize - 1) :
                (mouseX - mouseX % squareSize) / squareSize - 1;
        
        int row = fenReader.isWhitesTurn() ? 
                7 - ((mouseY - mouseY % squareSize) / squareSize - 1) : 
                ((mouseY - mouseY % squareSize) / squareSize - 1);
                
        return board[row * 8 + col];
    }
    
    private int getBoardIndex(int mouseX, int mouseY){
        int col = fenReader.isWhitesTurn() ? 
                7 - ((mouseX - mouseX % squareSize) / squareSize - 1) :
                (mouseX - mouseX % squareSize) / squareSize - 1;
        
        int row = fenReader.isWhitesTurn() ? 
                7 - ((mouseY - mouseY % squareSize) / squareSize - 1) : 
                ((mouseY - mouseY % squareSize) / squareSize - 1);
        
        return row * 8 + col;
    }
    
    private boolean mouseIsInBoard(){
        return (InputHandler.getX() >= 80 && InputHandler.getX() <= 720) &&
               (InputHandler.getY() >= 80 && InputHandler.getY() <= 720);
    }
    
    //-------UPDATE METHOD--------
    
    public void update(){
        if(InputHandler.isPressed()){
            if(draggedPiece == null){
                if(mouseIsInBoard()){
                    Piece piece = getPiece(InputHandler.getX(), InputHandler.getY());
                    if(fenReader.getColorToMove().equals(piece.getColorString())){
                        draggedPiece = piece;
                        draggedPieceIndex = getBoardIndex(InputHandler.getX(), InputHandler.getY());
                        validMoves = draggedPiece.getValidMoves();
                    }
                }
            }
        }
        else{
            if(draggedPiece != null){
                int targetPieceIndex = getBoardIndex(InputHandler.getX(), InputHandler.getY());
                
                Move move = MoveHandler.getMove(validMoves, targetPieceIndex);
                if(move != null){
                    move.execute();
                    previousMove = move;
                    fenReader.updatePositions(board);
                    fenReader.switchTurn();
                    fenReader.updateCastlingRights(MoveHandler.updateCastlingRights(move));
                    fenReader.updateEnPassantSquare(MoveHandler.updateEnPassantSquareCoordinate(move));
                    fenReader.updateMoveClocks(move);
                    //System.out.println(fenReader.getFen());
                    updateTypeBoard();
                    AttackMap.updateAttackMap();
                    AttackMap.printAttackMap();
                }
                
                draggedPieceIndex = -1;
                draggedPiece = null;
                validMoves.clear();
            }
        }
    }
    
    //-------DRAW METHODS--------
    
    public void draw(Graphics2D g2d){
        drawBackground(g2d);
        drawSquares(g2d);
        drawValidSquares(g2d);
        drawPreviousMove(g2d);
        drawPieces(g2d);
        drawDraggedPiece(g2d);
    }
    
    private void drawBackground(Graphics2D g2d){
        g2d.drawImage(ImageLoader.getBackground(), 0, 0, null);
    }
    
    private void drawSquares(Graphics2D g2d){        
        for(int row = 7; row >= 0; row--){
            for(int col = 0; col < 8; col++){
                int drawX = (col + 1) * squareSize;
                int drawY = (row + 1) * squareSize;
                g2d.setColor(((row + col) % 2 == 0) ? ImageLoader.lightSquareColor() : ImageLoader.darkSquareColor());
                g2d.fillRect(drawX, drawY, squareSize, squareSize);              
            }
        }
    }
    
    private void drawValidSquares(Graphics2D g2d){
        if(draggedPiece == null)
            return;
        for(Move move: validMoves){
            g2d.setColor(ImageLoader.getMoveColor(move));
            int in = move.getTargetIndex();
            int drawX = (draggedPiece.isWhite()) ? (7- in % 8) * squareSize + squareSize :
                    in % 8 * squareSize + squareSize;
            
            int drawY = (draggedPiece.isWhite()) ? (7- in / 8) * squareSize + squareSize :
                    in / 8 * squareSize + squareSize;
            
            g2d.fillRect(drawX + 15, drawY + 15, squareSize - 30, squareSize - 30);
        }
    }
    
    private void drawPieces(Graphics2D g2d){
        for(int row = 7; row >= 0; row--){
            for(int col = 0; col < 8; col++){
                int index = (fenReader.isWhitesTurn()) ? (63 - (row * 8 + col)) : (row * 8 + col); 
                int drawX = (col + 1) * squareSize;
                int drawY = (row + 1) * squareSize;
                if(!board[index].isBlank() && index != draggedPieceIndex){
                     g2d.drawImage(ImageLoader.getPieceImageByType(board[index].getType()), drawX, drawY, null);
                }
            }
        }
    }
    
    private void drawPreviousMove(Graphics2D g2d){
        if(previousMove == null)
            return;
        g2d.setColor(ImageLoader.prevMoveColor());
        int startRow = (previousMove.getStartIndex() / 8);
        int startCol = (previousMove.getStartIndex() % 8);
        int targetRow = (previousMove.getTargetIndex() / 8);
        int targetCol = (previousMove.getTargetIndex() % 8);
        
        int drawXStart = fenReader.isWhitesTurn() ? 
                560 - (startCol - 1) * squareSize:
                (startCol + 1) * squareSize;
        
        int drawYStart = fenReader.isWhitesTurn() ? 
                560 - (startRow - 1) * squareSize : 
                (startRow + 1) * squareSize;
        
        int drawXTarget = fenReader.isWhitesTurn() ? 
                560 - (targetCol - 1) * squareSize:
                (targetCol + 1) * squareSize;
        
        int drawYTarget = fenReader.isWhitesTurn() ? 
                560 - (targetRow - 1) * squareSize : 
                (targetRow + 1) * squareSize;
                
        g2d.fillRoundRect(drawXStart + 20, drawYStart + 20, squareSize - 40, squareSize - 40, 200, 200);
        g2d.fillRoundRect(drawXTarget + 20, drawYTarget + 20, squareSize - 40, squareSize - 40, 200, 200);
    }
    
    private void drawDraggedPiece(Graphics2D g2d){
        if(draggedPiece == null)
            return;
        if(InputHandler.isPressed() && !draggedPiece.isBlank())
            g2d.drawImage(ImageLoader.getPieceImageByType(draggedPiece.getType()), InputHandler.getX() - 40, InputHandler.getY() - 40, null);
    }
    
    //-------TO STRING METHOD--------
    
    @Override 
    public String toString(){
        String str = "";
        for(int row = 7; row >= 0; row--){
            for(int col = 7; col >= 0; col--){
                str += board[row * 8 + col].getIndex() + " ";
            }
            str += "\n";
        }
        return str;
    }
    
}
