/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.krlv.source.chessenginev1_4.gfx;

import com.krlv.source.chessenginev1_4.moves.Move;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author kvill
 */
public class ImageLoader {
    
    private static final Color darkSquare = new Color(122, 86, 49);
    private static final Color lightSquare = new Color(194, 135, 76);
    private static final Color regularMoveColor = new Color(189, 184, 92);
    private static final Color captureMoveColor = new Color(209, 71, 71);
    private static final Color enPassantMoveColor = new Color(201, 103, 103);
    private static final Color prevMoveColor = new Color(159, 94, 191);
    private static final Color castlingMoveColor = new Color(163, 163, 163);
    
    private static BufferedImage background;
    private static BufferedImage[] whitePieces, blackPieces;
    
    public static void preloadAssets(){
        whitePieces = new BufferedImage[6];
        blackPieces = new BufferedImage[6];
        try {
            background = ImageIO.read(new File("res/imgs/background.png"));
            
            whitePieces[0] = ImageIO.read(new File("res/imgs/whitepawn.png"));
            whitePieces[1] = ImageIO.read(new File("res/imgs/whiteknight.png"));
            whitePieces[2] = ImageIO.read(new File("res/imgs/whitebishop.png"));
            whitePieces[3] = ImageIO.read(new File("res/imgs/whiterook.png"));
            whitePieces[4] = ImageIO.read(new File("res/imgs/whitequeen.png"));
            whitePieces[5] = ImageIO.read(new File("res/imgs/whiteking.png"));
            
            blackPieces[0] = ImageIO.read(new File("res/imgs/blackpawn.png"));
            blackPieces[1] = ImageIO.read(new File("res/imgs/blackknight.png"));
            blackPieces[2] = ImageIO.read(new File("res/imgs/blackbishop.png"));
            blackPieces[3] = ImageIO.read(new File("res/imgs/blackrook.png"));
            blackPieces[4] = ImageIO.read(new File("res/imgs/blackqueen.png"));
            blackPieces[5] = ImageIO.read(new File("res/imgs/blackking.png"));
            
        } catch (IOException ex) {}
    }
    
    public static BufferedImage getPieceImageByType(int type){
        return (type > 0) ? whitePieces[type-1] : blackPieces[Math.abs(type+1)];
    }
    
    public static Color getMoveColor(Move move){
        switch(move.getType()){
            case REGULAR : return regularMoveColor;
            case CAPTURE : return captureMoveColor;
            case ENPASSANT : return enPassantMoveColor;
            case CASTLING : return castlingMoveColor;
            default : return Color.black;
        }
    }
    
    public static BufferedImage getBackground(){
        return background;
    }
    
    public static Color lightSquareColor(){
        return lightSquare;
    }
    
    public static Color darkSquareColor(){
        return darkSquare;
    }
    
    public static Color regularMoveColor(){
        return regularMoveColor;
    }
    
    public static Color captureMoveColor(){
        return captureMoveColor;
    }
    
    public static Color prevMoveColor(){
        return prevMoveColor;
    }
    
}
