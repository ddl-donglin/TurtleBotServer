package com.turtlebot.operation.utils.UDPServer;

import java.io.IOException;
import java.net.DatagramSocket;

public class DataRecv {

    public DataRecv() throws IOException {

        DatagramSocket dsRecv = new DatagramSocket(8888);

        RecvThread rt = new RecvThread(dsRecv);

        Thread recv = new Thread(rt);

        recv.start();

    }
}
