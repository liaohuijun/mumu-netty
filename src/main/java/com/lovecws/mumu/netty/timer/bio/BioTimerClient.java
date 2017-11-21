package com.lovecws.mumu.netty.timer.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: timer客户端
 * @date 2017-11-17 9:46
 */
public class BioTimerClient {

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 200; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Socket socket = new Socket();
                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    try {
                        socket.connect(new InetSocketAddress(BioTimerServer.HOST, BioTimerServer.PORT));
                        outputStream = socket.getOutputStream();
                        outputStream.write(("hello world " + new Date().toLocaleString()).getBytes());
                        outputStream.flush();

                        inputStream = socket.getInputStream();
                        byte[] bs = new byte[inputStream.available()];
                        inputStream.read(bs);
                        System.out.println(new String(bs));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (inputStream != null) inputStream.close();
                            if (outputStream != null) outputStream.close();
                            if (socket != null) socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

    }
}
