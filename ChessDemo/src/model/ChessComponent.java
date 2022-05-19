package model;

import view.Chessboard;
import view.ChessboardPoint;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类是一个抽象类，主要表示8*8棋盘上每个格子的棋子情况，当前有两个子类继承它，分别是EmptySlotComponent(空棋子)和RookChessComponent(车)。
 */
public abstract class ChessComponent extends JComponent {
    private char name;
    public static final Color A   = new Color(255, 209, 164);
    public static final Color B   = new Color(165, 84, 4);
    /**
     * CHESSGRID_SIZE: 主要用于确定每个棋子在页面中显示的大小。
     * <br>
     * 在这个设计中，每个棋子的图案是用图片画出来的（由于国际象棋那个棋子手动画太难了）
     * <br>
     * 因此每个棋子占用的形状是一个正方形，大小是50*50
     */
    private static final Color[] BACKGROUND_COLORS = {A, B};

    private static final Dimension CHESSGRID_SIZE = new Dimension(1080 / 4 * 3 / 8, 1080 / 4 * 3 / 8);
    /**
     * handle click event
     */
    private ClickController clickController;

    /**
     * chessboardPoint: 表示8*8棋盘中，当前棋子在棋格对应的位置，如(0, 0), (1, 0), (0, 7),(7, 7)等等
     * <br>
     * chessColor: 表示这个棋子的颜色，有白色，黑色，无色三种
     * <br>
     * selected: 表示这个棋子是否被选中
     */
    private ChessboardPoint chessboardPoint;
    protected final ChessColor chessColor;
    private boolean selected;
    private boolean undermouse;


    private boolean attacked;

    public boolean isAttacked() {
        return attacked;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    protected ChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLocation(location);
        setSize(size, size);
        this.chessboardPoint = chessboardPoint;
        this.chessColor = chessColor;
        this.selected = false;
        this.clickController = clickController;
        this.undermouse=false;

    }

    public ChessboardPoint getChessboardPoint() {
        return chessboardPoint;
    }

    public void setChessboardPoint(ChessboardPoint chessboardPoint) {
        this.chessboardPoint = chessboardPoint;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isundermouse(){return undermouse;}

    public void setundermouse(boolean undermouse){
        this.undermouse=undermouse;
    }

    /**
     * @param another 主要用于和另外一个棋子交换位置
     *                <br>
     *                调用时机是在移动棋子的时候，将操控的棋子和对应的空位置棋子(EmptySlotComponent)做交换
     */
    public void swapLocation(ChessComponent another) {
        ChessboardPoint chessboardPoint1 = getChessboardPoint(), chessboardPoint2 = another.getChessboardPoint();
        Point point1 = getLocation(), point2 = another.getLocation();
        setChessboardPoint(chessboardPoint2);
        setLocation(point2);
        another.setChessboardPoint(chessboardPoint1);
        another.setLocation(point1);
    }

    /**
     * @param e 响应鼠标监听事件
     *          <br>
     *          当接收到鼠标动作的时候，这个方法就会自动被调用，调用所有监听者的onClick方法，处理棋子的选中，移动等等行为。
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.printf("Click [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
            clickController.onClick(this);
        }else if(e.getID() == MouseEvent.MOUSE_ENTERED){
            System.out.printf("Move to [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
            clickController.notOnClick(this);
        }else if(e.getID()==MouseEvent.MOUSE_EXITED){
            clickController.leave(this);
        }
    }

    /**
     * @param chessboard  棋盘
     * @param destination 目标位置，如(0, 0), (0, 7)等等
     * @return this棋子对象的移动规则和当前位置(chessboardPoint)能否到达目标位置
     * <br>
     * 这个方法主要是检查移动的合法性，如果合法就返回true，反之是false
     */
    public abstract boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination);

    /**
     * 这个方法主要用于加载一些特定资源，如棋子图片等等。
     *
     * @throws IOException 如果一些资源找不到(如棋子图片路径错误)，就会抛出异常
     */
    public abstract void loadResource() throws IOException;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        System.out.printf("repaint chess [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
        Color squareColor = BACKGROUND_COLORS[(chessboardPoint.getX() + chessboardPoint.getY()) % 2];
        g.setColor(squareColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if(isAttacked()){// Highlights the model if selected.
            g.setColor(new Color(160,160,230));
            g.fillOval(29, 29, getWidth()/4 , getHeight()/4);
        }
    }


    //遍历棋盘，获取选中的棋子可以移动的点
    public List<ChessboardPoint> getPointsCanMoveTo(ChessComponent[][] chessboard) {
        ArrayList<ChessboardPoint> points = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessboardPoint destination = new ChessboardPoint(i,j);
                if(canMoveTo(chessboard,destination)
                        && chessboard[i][j].getChessColor() != chessColor
                        && !virtualChessboard(chessboard,destination)){
                    points.add(new ChessboardPoint(i,j));
                }
            }
        }
        return points;
    }

    //虚拟运行棋盘，每次调用都只走一次，用于判断行棋后己方王是否会被将军,在各个棋子的canMoveTo调用
    public boolean virtualChessboard(ChessComponent[][] chessComponents, ChessboardPoint destination){
        ChessComponent[][] a = new ChessComponent[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(chessComponents[i][j] instanceof BishopChessComponent){
                    a[i][j] = new BishopChessComponent(chessComponents[i][j].chessboardPoint,chessComponents[i][j].getLocation(),chessComponents[i][j].chessColor,null,0);
                }else if(chessComponents[i][j] instanceof EmptySlotComponent){
                    a[i][j] = new EmptySlotComponent(chessComponents[i][j].chessboardPoint,chessComponents[i][j].getLocation(),null,0);
                }else if(chessComponents[i][j] instanceof RookChessComponent){
                    a[i][j] = new RookChessComponent(chessComponents[i][j].chessboardPoint,chessComponents[i][j].getLocation(),chessComponents[i][j].chessColor,null,0);
                }else if(chessComponents[i][j] instanceof KnightChessComponent){
                    a[i][j] = new KnightChessComponent(chessComponents[i][j].chessboardPoint,chessComponents[i][j].getLocation(),chessComponents[i][j].chessColor,null,0);
                }else if(chessComponents[i][j] instanceof KingChessComponent){
                    a[i][j] = new KingChessComponent(chessComponents[i][j].chessboardPoint,chessComponents[i][j].getLocation(),chessComponents[i][j].chessColor,null,0);
                }else if(chessComponents[i][j] instanceof PawnChessComponent){
                    a[i][j] = new PawnChessComponent(chessComponents[i][j].chessboardPoint,chessComponents[i][j].getLocation(),chessComponents[i][j].chessColor,null,0);
                }else if(chessComponents[i][j] instanceof QueenChessComponent){
                    a[i][j] = new QueenChessComponent(chessComponents[i][j].chessboardPoint,chessComponents[i][j].getLocation(),chessComponents[i][j].chessColor,null,0);
                }
            }
        }//复制棋盘
        int sourceX = this.chessboardPoint.getX();
        int sourceY = this.chessboardPoint.getY();
        int targetX = destination.getX();
        int targetY = destination.getY();
        if(targetX<8 &&targetY <8 &&targetX >=0 && targetY>=0 && !(targetX ==sourceX && targetY ==sourceY)
                && a[sourceX][sourceY].canMoveTo(a,destination)){
            a[sourceX][sourceY].setChessboardPoint(a[targetX][targetY].getChessboardPoint());
            a[targetX][targetY] = a[sourceX][sourceY];
            a[sourceX][sourceY] = new EmptySlotComponent(new ChessboardPoint(sourceX, sourceY), chessComponents[sourceX][sourceY].getLocation(),null,0);
            if(a[targetX][targetY].getChessColor() == ChessColor.WHITE){
                return whiteKingIsCheckmated(a);
            }else if(a[targetX][targetY].getChessColor() == ChessColor.BLACK){
                return blackKingIsCheckmated(a);
            }else {
                return false;
            }
        }else{
            return false;
        }
    }

    //判定白王是否被将
    public boolean whiteKingIsCheckmated(ChessComponent[][] chessComponents){

        int x = 0;
        int y = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(chessComponents[i][j] instanceof KingChessComponent
                        && chessComponents[i][j].getChessColor() == ChessColor.WHITE){
                    x = i;y = j;
                    break;
                }
            }
        }
        ChessComponent king = chessComponents[x][y];//找白王
        for (int i1 = 0; i1 < 8; i1++) {
            for (int j1 = 0; j1 < 8; j1++) {
                if (chessComponents[i1][j1].getChessColor()==ChessColor.BLACK
                        && chessComponents[i1][j1].canMoveTo(chessComponents,king.getChessboardPoint())){
                    return true;
                }
            }
        }

        return false;
    }

    //判定黑王是否被将
    public boolean blackKingIsCheckmated(ChessComponent[][] chessComponents){

        int x = 0;
        int y = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(chessComponents[i][j] instanceof KingChessComponent
                        && chessComponents[i][j].getChessColor() == ChessColor.BLACK){
                    x = i;y = j;
                    break;
                }
            }
        }
        ChessComponent king = chessComponents[x][y];//找黑王
        for (int i1 = 0; i1 < 8; i1++) {
            for (int j1 = 0; j1 < 8; j1++) {
                if (chessComponents[i1][j1].getChessColor()==ChessColor.WHITE
                        && chessComponents[i1][j1].canMoveTo(chessComponents,king.getChessboardPoint())){
                    return true;
                }
            }
        }

        return false;
    }

}
