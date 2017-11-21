package com.lovecws.mumu.netty.timer.nio;

import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: nio时间服务器
 * @date 2017-11-17 10:48
 */
public class NioTimerServer {

    public static final String HOST = "localhost";
    public static final int PORT = 9999;

    public static void main(String[] args) throws IOException {
        new NioTimerWorker(HOST,PORT).start();
    }
}
