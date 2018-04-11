package com.turtlebot.operation.utils.UDPServer;

import java.net.DatagramSocket;
import java.net.SocketException;

public class DataSend {

    public DataSend(String data,String host, int port) throws SocketException {

        DatagramSocket dsSend = new DatagramSocket();

        SendThread st = new SendThread(dsSend,data,host,port);

        Thread send = new Thread(st);

        send.start();

    }
}
