package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class InputListener implements ActionListener {
    private Chessboard chessboard;
    JFrame loginFrame=new JFrame();

    public void setCurrentChessBoard(String currentChessBoard) {
        CurrentChessBoard = currentChessBoard;
    }

    public String getCurrentChessBoard() {
        return CurrentChessBoard;
    }

    private String CurrentChessBoard;
   public InputListener(JFrame loginFrame){
       this.loginFrame=loginFrame;
   }


    @Override
    public void actionPerformed(ActionEvent e) {

            // Display an open file chooser

        JFileChooser fileChooser  = new JFileChooser("C:\\Users\\DELL\\Desktop\\ChessDemo_1_\\ChessDemo\\游戏存档");


        int returnValue = fileChooser.showOpenDialog(null);

        loginFrame.setVisible(false); // 隐藏登录窗口，游戏进行中不要关闭程序
        ChessGameFrame.fatherFrame = loginFrame;
        ChessGameFrame.main(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("we selected: " + selectedFile);
                InputStream fis = null;
                try {
                    fis = new FileInputStream(selectedFile);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
// 3. 进行读操作
                byte[] buf = new byte[1024];    // 所有的内容读到此数组中临时存放
                int len = 0;    //用于记录读取的数据个数
                String myStr = "";
                while(true) {
                    try {
                        if (!((len = fis.read(buf)) != -1)) break;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }   //将内容读到byte数组中，同时返回个数，若为-1，则内容读到底
                    try {
                        myStr += new String(buf, 0, len, "UTF-8");
                    } catch (UnsupportedEncodingException ex) {
                        ex.printStackTrace();
                    }
                }
// 4. 关闭输出
                try {
                    fis.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                CurrentChessBoard=myStr;
                File f = new File("D:\\Chessww\\hhh.txt");//指定文件
                FileOutputStream fos = null;//创建输出流fos并以f为参数
                try {
                    fos = new FileOutputStream(f);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                OutputStreamWriter osw = new OutputStreamWriter(fos);//创建字符输出流对象osw并以fos为参数
                BufferedWriter bw = new BufferedWriter(osw);//创建一个带缓冲的输出流对象bw，并以osw为参数
                try {
                    bw.write(myStr);//使用bw写入一行文字，为字符串形式String
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    bw.newLine();//换行
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    bw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println(myStr);

            }

    }
}
