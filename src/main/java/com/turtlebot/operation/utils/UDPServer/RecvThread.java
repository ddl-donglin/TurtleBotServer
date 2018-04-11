package com.turtlebot.operation.utils.UDPServer;



import com.turtlebot.operation.utils.FileUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class RecvThread implements Runnable{
    private DatagramSocket ds;

    private String defaultFileAdd = "";

    public RecvThread(DatagramSocket dsRecv) {
        this.ds = dsRecv;
    }

    @Override
    public void run() {
        try{
            FileUtil.clearFile(defaultFileAdd+"recvData.txt");
            FileUtil.writeFile("\n------------------------------------------\n",defaultFileAdd+"recvDataBack.txt");
            while(true){
                //创建数据包
                byte[] bys = new byte[1024];
                DatagramPacket dp = new DatagramPacket(bys, bys.length);
                //调用Socket对象的接收方法
                ds.receive(dp);

                String ip = dp.getAddress().getHostAddress();
                String recv = new String(dp.getData(), 0 , dp.getLength());
                System.out.println("from" + ip + "发来的数据是：" + recv);
                FileUtil.writeFile(recv+"\n",defaultFileAdd+"recvData.txt");
                FileUtil.writeFile(recv,defaultFileAdd+"recvDataBack.txt");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
