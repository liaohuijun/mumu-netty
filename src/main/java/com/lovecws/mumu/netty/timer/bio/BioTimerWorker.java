package com.lovecws.mumu.netty.timer.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 工作线程
 * @date 2017-11-17 9:46
 */
public class BioTimerWorker extends Thread {

    private Socket socket;

    public BioTimerWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            //获取客户端的输入
            inputStream = socket.getInputStream();
            byte[] bs = new byte[inputStream.available()];
            inputStream.read(bs);
            System.out.println(Thread.currentThread().getName() + " " + new String(bs));

            //向客户端返回数据
            outputStream = socket.getOutputStream();
            outputStream.write(("server recall :lovercws" + new Date().toLocaleString()).getBytes());
            outputStream.flush();
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
}
