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

public class QueenChessComponent extends ChessComponent {
    private static Image KING_WHITE;
    private static Image KING_BLACK;
    private Image kingImage;

    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("images/queen-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("images/queen-black.png"));
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

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        this.initiateKingImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if(chessComponents[destination.getX()][destination.getY()].getChessColor()
                != this.getChessColor()){
            int sum = source.getX() + source.getY();
            int dif = source.getX() - source.getY();
            if (dif == destination.getX()-destination.getY()) {
                for (int x = Math.min(source.getX(), destination.getX()) + 1;
                     x < Math.max(source.getX(), destination.getX()); x++) {
                    if (!(chessComponents[x][x-dif] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else if (sum == destination.getX() + destination.getY()) {
                for (int x = Math.min(source.getX(), destination.getX()) + 1;
                     x < Math.max(source.getX(), destination.getX()); x++) {
                    if (!(chessComponents[x][sum-x] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else if (source.getX() == destination.getX()) {
                int row = source.getX();
                for (int col = Math.min(source.getY(), destination.getY()) + 1;
                     col < Math.max(source.getY(), destination.getY()); col++) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else if (source.getY() == destination.getY()) {
                int col = source.getY();
                for (int row = Math.min(source.getX(), destination.getX()) + 1;
                     row < Math.max(source.getX(), destination.getX()); row++) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else { // Not on the diagonal and the same row or column.
                return false;
            }
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
