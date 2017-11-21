package com.lovecws.mumu.netty.timer.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: timer服务端
 * @date 2017-11-17 9:47
 */
public class BioTimerServer {

    public static final String HOST = "localhost";
    public static final int PORT = 9999;
    private static ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(HOST, PORT));
            System.out.println("server starting...........");
            while (true) {
                Socket socket = serverSocket.accept();
                //new BioTimerWorker(socket).start();
                executorService.execute(new BioTimerWorker(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
