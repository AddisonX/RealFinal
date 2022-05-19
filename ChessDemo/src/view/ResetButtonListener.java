package view;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ResetButtonListener implements ActionListener {
    public Chessboard chessboard;

    public ResetButtonListener(Chessboard chessboard) {
        this.chessboard = chessboard;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        chessboard.ResetChessBoard();
        return;
    }
}