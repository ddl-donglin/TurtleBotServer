package com.turtlebot.operation.utils.TCPServer;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 描述:
 * TcpPicServer
 *
 * @outhor didonglin
 * @create 2018-03-04 20:25
 */
public class TcpPicServer {
    public static void serverPic(Integer port, String recvname) throws IOException {
         // 创建ServerSocket对象，监听
        ServerSocket ss = new ServerSocket(port);

        // 创建Socket对象
        Socket s = ss.accept();

        // 把通道中的字节流转成缓冲字节流
        BufferedInputStream bi = new BufferedInputStream(s.getInputStream());

        // 封装图片目录
        BufferedOutputStream bo = new BufferedOutputStream(
                new FileOutputStream(recvname));

        // 获取图片的数据，并输出
        byte[] bys = new byte[1024];
        int len = 0;
        while ((len = bi.read(bys)) != -1) {
            bo.write(bys, 0, len);
            bo.flush();
        }

        //给客户端反馈
        OutputStream op = s.getOutputStream();
        op.write("图片上传成功".getBytes());

        //释放资源
        s.close();
        bo.close();
    }

    public static void main(String[] args) throws IOException {
        TcpPicServer.serverPic(8888,"test.jpg");
    }
}