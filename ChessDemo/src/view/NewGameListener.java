package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

class NewGameListener implements ActionListener {
    private final JFrame loginFrame;
    public NewGameListener(JFrame loginFrame) {

        this.loginFrame = loginFrame;
    }

    private long len;
    private String path;
    private File src;
    private int FileNum;
    private int DirNum;

    public int DirCount(String path) {
        this.path=path;
        this.src=new File(path);
        count(this.src);
        return FileNum;
    }
    //统计大小
    private void count(File src) {
        if(null!=src||src.exists()) {//递归头
            if(src.isFile()) {//文件
                len+=src.length();
                this.FileNum++;
            }else {//目录
                this.DirNum++;
                for(File s:src.listFiles()) {
                    count(s);
                }
            }
        }
    }




     @Override
    public void actionPerformed(ActionEvent e) {
                loginFrame.setVisible(false); // 隐藏登录窗口，游戏进行中不要关闭程序
                ChessGameFrame.fatherFrame = loginFrame;
                ChessGameFrame.main(null);
         int count=(DirCount("C:\\Users\\DELL\\Desktop\\ChessDemo_1_\\ChessDemo\\游戏存档"));
        File file=new File("C:\\Users\\DELL\\Desktop\\ChessDemo_1_\\ChessDemo\\游戏存档\\Game"+(count+1)+".txt");
         try {
             file.createNewFile();
         } catch (IOException ex) {
             ex.printStackTrace();
         }

     }
    }
