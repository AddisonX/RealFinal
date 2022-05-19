//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package model;

import controller.ClickController;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import view.ChessboardPoint;

public class KingChessComponent extends ChessComponent {
    private static Image KING_WHITE;
    private static Image KING_BLACK;
    private Image kingImage;


    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("images/king-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("images/king-black.png"));
        }

    }

    private void initiateKingImage(ChessColor color) {
        try {
            this.loadResource();
            if (color == ChessColor.WHITE) {
                this.kingImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                this.kingImage = KING_BLACK;
            }
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        this.initiateKingImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = this.getChessboardPoint();
        if (Math.abs(destination.getX() - source.getX()) == 1 && Math.abs(destination.getY() - source.getY())== 1) {
            return true;
        } else if(Math.abs(destination.getX() - source.getX()) == 0 && Math.abs(destination.getY() - source.getY())== 1){
            return true;
        }else if(Math.abs(destination.getX() - source.getX()) == 1 && Math.abs(destination.getY() - source.getY())== 0) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            this.setundermouse(false);

            Color squareColor = new Color(55,173,232);
            g.setColor(squareColor);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.drawImage(this.kingImage, 0, 0, this.getWidth(), this.getHeight(), this);
            g.setColor(Color.BLACK);        }
        if(isAttacked()){// Highlights the model if selected.
            g.setColor(Color.gray);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
        if(this.isundermouse()){
            Color squareColor = new Color(213,200,12);
            g.setColor(squareColor);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.drawImage(this.kingImage, 0, 0, this.getWidth(), this.getHeight(), this);
            g.setColor(Color.BLACK);
        }


    }
}
