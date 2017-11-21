package com.lovecws.mumu.netty.timer.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: aio时间服务器
 * @date 2017-11-17 14:19
 */
public class AioTimerServer {

    public static final String HOST = "localhost";
    public static final int PORT = 9999;

    public static void main(String[] args) throws IOException {
        new AsyncTimerServerHandler().start();
    }

    public static class AsyncTimerServerHandler extends Thread {
        private AsynchronousServerSocketChannel serverSocketChannel = null;
        private CountDownLatch countDownLatch = new CountDownLatch(1);

        public AsyncTimerServerHandler() {
            try {
                serverSocketChannel = AsynchronousServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress(HOST, PORT));
                System.out.println("aio timer server starting........");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            serverSocketChannel.accept(this, new AsyncTimerCompletionHandler());
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class AsyncTimerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimerServerHandler> {
        @Override
        public void completed(final AsynchronousSocketChannel result, final AsyncTimerServerHandler attachment) {
            attachment.serverSocketChannel.accept(attachment, this);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            result.read(byteBuffer, byteBuffer, new ReadTimerCompletionHandler(result));
        }

        @Override
        public void failed(final Throwable exc, final AsyncTimerServerHandler attachment) {
            attachment.countDownLatch.countDown();
        }
    }

    public static class ReadTimerCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

        private AsynchronousSocketChannel socketChannel;

        public ReadTimerCompletionHandler(AsynchronousSocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void completed(final Integer result, final ByteBuffer attachment) {
            attachment.flip();
            byte[] body = new byte[attachment.remaining()];
            attachment.get(body);
            System.out.println("the timer server " + new String(body));
            handleWrite("lovecws");
        }

        @Override
        public void failed(final Throwable exc, final ByteBuffer attachment) {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void handleWrite(String response) {
            if (response == null) response = "";
            ByteBuffer byteBuffer = ByteBuffer.wrap(response.getBytes());
            socketChannel.write(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(final Integer result, final ByteBuffer attachment) {
                    if (attachment.hasRemaining()) {
                        socketChannel.write(attachment, attachment, this);
                    }
                }

                @Override
                public void failed(final Throwable exc, final ByteBuffer attachment) {
                    try {
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
