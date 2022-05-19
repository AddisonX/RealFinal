package view;

import javax.swing.*;
import java.awt.*;

public class MainMenus {
    JFileChooser fileChooser  = new JFileChooser("C:\\Users\\DELL\\Desktop\\ChessDemo_1_\\ChessDemo\\游戏存档");
    public static void main(String[] args) {
        JFrame loginFrame = new JFrame("主菜单");
        Chessboard chessboard;
        loginFrame.setSize(500,700);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setResizable(false);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImagePanel imagePanel=new ImagePanel("images/Loginin！.png");
        imagePanel.setLayout(null);
        loginFrame.add(imagePanel);

//        JPanel Panel=new JPanel();


        JButton startbutton=new JButton("新游戏");
//        startbutton.setPreferredSize(new Dimension(150,50));
//        startbutton.setContentAreaFilled(false);
        startbutton.setBounds(175,200,150,50);
        imagePanel.add(startbutton);
        startbutton.addActionListener(new NewGameListener(loginFrame));

        JButton button2=new JButton("加载存档");
        button2.setBounds(175,275,150,50);
        imagePanel.add(button2);
        button2.addActionListener(new InputListener(loginFrame));

        JButton button3=new JButton("游戏设置");
        button3.setBounds(175,350,150,50);
        imagePanel.add(button3);
        loginFrame.setVisible(true);
    }
}
