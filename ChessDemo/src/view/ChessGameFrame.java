package view;

import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    static JFrame fatherFrame;

    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private JLabel jl3;
//    private Chessboard chessboard=new Chessboard(1000, 760);
//    public int currentchesscolorcnt=0;

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        try {
            addChessboard();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        addLabel();
//        addHelloButton();
        addLoadButton();
//        ImageIcon icon1 =new ImageIcon("images/JP)PS}S~)}_HW20L)MC(2N7.png" );
//        JLabel Background = new JLabel(icon1);
//        add(Background);
//        setVisible(true);
        addBackgroundImage();

    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() throws IOException {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        File F=new File("D:\\Chessww\\hhh.txt");//指定文件
//        FileInputStream fis=new FileInputStream(F);//创建输入流fis并以f为参数
//        InputStreamReader isr=new InputStreamReader(fis);//创建字符输入流对象isr并以fis为参数
//        BufferedReader br=new BufferedReader(isr);//创建一个带缓冲的输入流对象br，并以isr为参数
//        String result=br.readLine();//使用bufferedreader读取一行文字并将读取值赋给字符串result。每执行一次br.readLine();,就会往下读取一行。
//        chessboard.LoadChessBoard(result);
//        chessboard.repaint();
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);


        JLabel statusLabel = new JLabel("Round: White");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        chessboard.setColorLabel(statusLabel);
        add(statusLabel);

        JLabel chessmateLabel = new JLabel(" ");
        chessmateLabel.setLocation(HEIGTH, HEIGTH / 10+30);
        chessmateLabel.setSize(200, 60);
        chessmateLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        chessmateLabel.setForeground(Color.red);
//        chessboard.setColorLabel(statusLabel);
        chessboard.setLabel(chessmateLabel);
        add(chessmateLabel);

        JButton button=new JButton("Reset");
        button.setLocation(HEIGTH,HEIGTH/10+300);
        button.setSize(200,60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(new ResetButtonListener(chessboard));


//        JButton InputButton=new JButton("Input ChessBoard");
//        InputButton.setLocation((HEIGTH,HEIGTH/10+460);
//        InputButton.setSize(200,60);
//        InputButton.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(InputButton);
//        InputButton.addActionListener(new );
    }



    /**
     * 在游戏面板中添加标签
     */
//    private void addLabel() {
////        String currentchesscolor=chessboard.getCurrentColor== ChessColor.WHITE?"White":"Black";
//        JLabel statusLabel = new JLabel();
//        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
//        statusLabel.setSize(200, 60);
//        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
//        new Chessboard().
//        add(statusLabel);
//    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

//    private void addHelloButton() {
//        JButton button = new JButton("Show Hello Here");
//        button.addActionListener((e) -> );
//        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//    }


//    private void init(){
//
//    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            gameController.loadGameFromFile(path);
        });
    }

//    private void addRetButton(){
//        JButton button=new JButton("Reset");
//        button.setLocation(HEIGTH,HEIGTH/10+300);
//        button.setSize(200,60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//        button.addActionListener(new ResetButtonListener());
//    }



    private void addBackgroundImage(){
        ImageIcon icon1 =new ImageIcon("images/JP)PS}S~)}_HW20L)MC(2N7.png" );
        JLabel Background = new JLabel(icon1);
        Background.setBounds(0,0,icon1.getIconWidth(), icon1.getIconHeight());
        add(Background);

    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
            mainFrame.setVisible(true);
        });
    }

}
