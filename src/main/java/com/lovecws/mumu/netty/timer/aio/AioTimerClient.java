package com.lovecws.mumu.netty.timer.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: aio客户端
 * @date 2017-11-17 14:46
 */
public class AioTimerClient {
    public static void main(String[] args) {
        new AsyncTimerClientHandler(AioTimerServer.HOST, AioTimerServer.PORT).start();
    }

    public static class AsyncTimerClientHandler extends Thread implements CompletionHandler<Void, AsyncTimerClientHandler> {

        private AsynchronousSocketChannel socketChannel;
        private CountDownLatch countDownLatch;
        private String host;
        private int port;

        public AsyncTimerClientHandler(String host, int port) {
            this.host = host;
            this.port = port;
            try {
                socketChannel = AsynchronousSocketChannel.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            countDownLatch = new CountDownLatch(1);
            socketChannel.connect(new InetSocketAddress(host, port), this, this);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void completed(final Void result, final AsyncTimerClientHandler attachment) {
            ByteBuffer byteBuffer = ByteBuffer.wrap("lovecws".getBytes());
            socketChannel.write(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(final Integer result, final ByteBuffer attachment) {
                    if (attachment.hasRemaining()) {
                        socketChannel.write(attachment, attachment, this);
                    } else {
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        socketChannel.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                            @Override
                            public void completed(final Integer result, final ByteBuffer buffer) {
                                buffer.flip();
                                byte[] bs = new byte[buffer.remaining()];
                                buffer.get(bs);
                                System.out.println(new String(bs));
                                countDownLatch.countDown();
                            }

                            @Override
                            public void failed(final Throwable exc, final ByteBuffer attachment) {
                                try {
                                    socketChannel.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                countDownLatch.countDown();
                            }
                        });
                    }
                }

                @Override
                public void failed(final Throwable exc, final ByteBuffer attachment) {
                    try {
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });
        }

        @Override
        public void failed(final Throwable exc, final AsyncTimerClientHandler attachment) {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }
    }
}
