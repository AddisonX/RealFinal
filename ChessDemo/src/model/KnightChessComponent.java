package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的车
 */
public class KnightChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image knightImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (KNIGHT_WHITE == null) {
            KNIGHT_WHITE = ImageIO.read(new File("images/knight-white.png"));
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(new File("images/knight-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = KNIGHT_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = KNIGHT_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKnightImage(color);
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if(source.getY()==destination.getY()&&source.getX()==destination.getX()){
            return false;
        }
        if(Math.pow(Math.abs(source.getX()-destination.getX()),2)+Math.pow(Math.abs(source.getY()-destination.getY()),2)==5){
            return true;
        }
        return false;
    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(knightImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            this.setundermouse(false);

            Color squareColor = new Color(55,173,232);
            g.setColor(squareColor);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.drawImage(this.knightImage, 0, 0, this.getWidth(), this.getHeight(), this);
            g.setColor(Color.BLACK);        }
        if(isAttacked()){// Highlights the model if selected.
            g.setColor(Color.gray);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
        if(this.isundermouse()){
            Color squareColor = new Color(213,200,12);
            g.setColor(squareColor);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.drawImage(this.knightImage, 0, 0, this.getWidth(), this.getHeight(), this);
            g.setColor(Color.BLACK);
        }
    }
}
