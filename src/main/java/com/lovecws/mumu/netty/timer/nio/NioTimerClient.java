package com.lovecws.mumu.netty.timer.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: nio客户端
 * @date 2017-11-17 11:22
 */
public class NioTimerClient {
    public static void main(String[] args) {
        SocketChannel socketChannel = null;
        Selector selector = null;
        boolean stop = true;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            selector = Selector.open();
            boolean connect = socketChannel.connect(new InetSocketAddress(NioTimerServer.HOST, NioTimerServer.PORT));
            if (connect) {
                socketChannel.register(selector, SelectionKey.OP_READ);
                handleWrite(socketChannel, "lovecws"+new Date().toLocaleString());
            } else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
            while (stop) {
                selector.select(100);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (!selectionKey.isValid()) {
                        continue;
                    }
                    try {
                        //处理连接请求
                        if (selectionKey.isConnectable()) {
                            SocketChannel socketChannel1 = (SocketChannel) selectionKey.channel();
                            if (socketChannel1.finishConnect()) {
                                socketChannel1.register(selector, SelectionKey.OP_READ);
                                handleWrite(socketChannel1, "lovecws");
                            } else {
                                System.out.println("连接失败");
                                System.exit(-1);
                            }
                        }
                        //处理读请求
                        else if (selectionKey.isReadable()) {
                            handleRead(selectionKey);
                            stop = false;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (selectionKey != null) selectionKey.cancel();
                        if (selectionKey.channel() != null) selectionKey.channel().close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (selector != null) {
                    selector.close();
                }
                if (socketChannel != null) {
                    socketChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理读请求
     *
     * @param selectionKey
     * @throws IOException
     */
    private static void handleRead(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        socketChannel.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read = socketChannel.read(byteBuffer);
        if (read > 0) {
            byteBuffer.flip();
            byte[] bs = new byte[byteBuffer.remaining()];
            byteBuffer.get(bs);
            System.out.println("服务端响应: " + new String(bs));
        } else if (read < 0) {
            selectionKey.cancel();
            socketChannel.close();
        }
    }

    /**
     * 处理写回调
     *
     * @param socketChannel
     * @param content
     * @throws IOException
     */
    private static void handleWrite(SocketChannel socketChannel, String content) throws IOException {
        if (content == null) content = "";
        socketChannel.write(ByteBuffer.wrap(content.getBytes()));
    }
}
