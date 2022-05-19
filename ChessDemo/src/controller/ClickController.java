package controller;


import model.ChessColor;
import model.ChessComponent;
import model.KnightChessComponent;
import model.PawnChessComponent;
import view.Chessboard;
import view.ChessboardPoint;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ClickController {
    private final Chessboard chessboard;
    public ChessComponent first;





    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) {

        List<ChessboardPoint> points = chessComponent.getPointsCanMoveTo(chessboard.getChessComponents());
        ArrayList<ChessComponent> chessComponents = new ArrayList<>();
        for (ChessboardPoint point : points) {
            chessComponents.add(getChess(point));
        }

        if (first == null) {//未选中任何棋子
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;


                for (ChessComponent component : chessComponents) {
                    component.setAttacked(true);
                    component.repaint();
                }
                first.repaint();
            }
        } else { //已选中棋子
            if (first == chessComponent) { // 再次点击取消选取
                for (ChessComponent component : chessComponents) {
                    component.setAttacked(false);
                    component.repaint();
                }
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {//点击合法行棋格点
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();//切换当前行棋方
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        chessboard.getChessComponents()[i][j].setAttacked(false);
                        chessboard.getChessComponents()[i][j].repaint();
                    }
                }
                first.setSelected(false);
                first.repaint();
                first = null;



            }else if(chessComponent.getChessColor()== chessboard.getCurrentColor()){//选中其他同色棋子

                first.setSelected(false);
                first.repaint();
                first = null;

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        chessboard.getChessComponents()[i][j].setAttacked(false);
                        chessboard.getChessComponents()[i][j].repaint();
                    }
                }

                for (ChessComponent component : chessComponents) {
                    component.setAttacked(true);
                    component.repaint();
                }
                chessComponent.setSelected(true);
                first=chessComponent;
                first.repaint();
            }

        }
    }

    public void notOnClick(ChessComponent chessComponent){
        if(chessComponent.getChessColor()== chessboard.getCurrentColor())
            chessComponent.setundermouse(true);
        chessComponent.repaint();
    }

    public void leave(ChessComponent chessComponent){
        chessComponent.setundermouse(false);
        chessComponent.repaint();
    }

        /**
         * @param chessComponent 目标选取的棋子
         * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
         */

        private boolean handleFirst(ChessComponent chessComponent) {
            return chessComponent.getChessColor() == chessboard.getCurrentColor();
        }

        /**
         * @param chessComponent first棋子目标移动到的棋子second
         * @return first棋子是否能够移动到second棋子位置
         */

        private boolean handleSecond(ChessComponent chessComponent) {

            return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                    first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint())
                    && !first.virtualChessboard(chessboard.getChessComponents(),chessComponent.getChessboardPoint());
        }

        //通过棋点找棋子
        private ChessComponent getChess(ChessboardPoint chessboardPoint){
            return chessboard.getChessComponents()[chessboardPoint.getX()][chessboardPoint.getY()];
        }

//        //判定白王是否被将
//    public boolean whiteKingIsCheckmated(ChessComponent[][] chessComponents){
//
//            int x = 0;
//            int y = 0;
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    if(chessComponents[i][j] instanceof KnightChessComponent
//                            && chessComponents[i][j].getChessColor() == ChessColor.WHITE){
//                        x = i;y = j;
//                        break;
//                    }
//                }
//            }
//            ChessComponent king = chessComponents[x][y];//找白王
//            for (int i1 = 0; i1 < 8; i1++) {
//                for (int j1 = 0; j1 < 8; j1++) {
//                    if (chessComponents[i1][j1].getChessColor()==ChessColor.BLACK
//                        && chessComponents[i1][j1].canMoveTo(chessComponents,king.getChessboardPoint())){
//                        return true;
//
//                    }
//                }
//            }
//
//            return false;
//    }
//
//    //判定黑王是否被将
//    public boolean blackKingIsCheckmated(ChessComponent[][] chessComponents){
//
//        int x = 0;
//        int y = 0;
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if(chessComponents[i][j] instanceof KnightChessComponent
//                        && chessComponents[i][j].getChessColor() == ChessColor.BLACK){
//                    x = i;y = j;
//                    break;
//                }
//            }
//        }
//        ChessComponent king = chessComponents[x][y];//找白王
//        for (int i1 = 0; i1 < 8; i1++) {
//            for (int j1 = 0; j1 < 8; j1++) {
//                if (chessComponents[i1][j1].getChessColor()==ChessColor.WHITE
//                        && chessComponents[i1][j1].canMoveTo(chessComponents,king.getChessboardPoint())){
//                    return true;
//
//                }
//            }
//        }
//
//        return false;
//    }
//
//    public void setLabel(){
//            if(whiteKingIsCheckmated(chessboard.getChessComponents())){
//                label.setText("white is chessmated!");
//            }
//            if(blackKingIsCheckmated(chessboard.getChessComponents())){
//                label.setText("black is chessmated!");
//            }
//    }
//判断白方是否输棋（如果所有棋子都无处可去则判负），如果输棋了则返回true
public boolean whiteIsFailed(ChessComponent[][] chessComponents){
    int counts = 0;
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            if(chessComponents[i][j].getChessColor() == ChessColor.WHITE){
                counts += chessComponents[i][j].getPointsCanMoveTo(chessComponents).size();
            }
        }
    }

    return counts == 0;
}

    //判断黑方是否输棋（如果所有棋子都无处可去则判负），如果输棋了则返回true
    public boolean blackIsFailed(ChessComponent[][] chessComponents){
        int counts = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(chessComponents[i][j].getChessColor() == ChessColor.BLACK){
                    counts += chessComponents[i][j].getPointsCanMoveTo(chessComponents).size();
                }
            }
        }

        return counts == 0;
    }


}
