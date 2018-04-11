package com.turtlebot.operation.utils.TCPServer;

import java.io.*;
import java.net.Socket;

/**
 * Created by didonglin on 2018/3/4.
 */

public class TcpPicClient {

    public TcpPicClient(){}

    public static void upload(String host, Integer port, String picinfo) throws IOException {
        // 创建Socket对象
        Socket s = new Socket(host, port);

        // 封装图片,图片只能使用字节流，为了高效，用缓冲字节流
        BufferedInputStream bi = new BufferedInputStream(new FileInputStream(
                picinfo));

        // 把通道中的字节流包装成缓冲字节流
        BufferedOutputStream bo = new BufferedOutputStream(s.getOutputStream());

        // 接收图片,并发送给服务器
        byte[] bys = new byte[1024];
        int len = 0;// 读取的实际长度，没有数据时，为-1
        while ((len = bi.read(bys)) != -1) {
            bo.write(bys, 0, len);
            bo.flush();
        }

        // 提醒服务器已经读取完毕，终止
        s.shutdownOutput();

        // 接收反馈
        InputStream in = s.getInputStream();
        byte[] by = new byte[1024];
        //肯定有内容的，就不判断了
        int len1 = in.read(by);
        String str = new String(by,0,len1);
        System.out.println(str);

        // 释放资源
        bi.close();
        s.close();
    }

    public static void main(String[] args) throws IOException {
        TcpPicClient.upload("192.168.20.132", 8888, "link.jpg");
    }
}
