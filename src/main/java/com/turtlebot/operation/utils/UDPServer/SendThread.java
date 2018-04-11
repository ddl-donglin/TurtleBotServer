package com.turtlebot.operation.utils.UDPServer;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendThread implements Runnable{
    private DatagramSocket ds;
    private String controldata;
    private String host;
    private int port;

    public SendThread(DatagramSocket dsSend,String controldata,String host, int port) {

        this.ds = dsSend;
        this.controldata = controldata;
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        InputStream is = new ByteArrayInputStream(controldata.getBytes());
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while((line = bf.readLine()) != null){

                ds.send(new DatagramPacket(line.getBytes(), line.getBytes().length,
                        InetAddress.getByName(host), port));

                if("88".equals(line))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
