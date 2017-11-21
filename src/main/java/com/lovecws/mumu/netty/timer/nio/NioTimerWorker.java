package com.lovecws.mumu.netty.timer.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description:
 * @date 2017-11-17 11:08
 */
public class NioTimerWorker extends Thread {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public NioTimerWorker(String host, int port) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(host, port));
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("nio timer server starting.........");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
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
                        if (selectionKey.isAcceptable()) {
                            handleAcceptable(selectionKey);
                        }
                        //处理读请求
                        else if (selectionKey.isReadable()) {
                            handleRead(selectionKey);
                        }
                    } catch (IOException e) {
                        if (selectionKey != null) selectionKey.cancel();
                        if (selectionKey.channel() != null) selectionKey.channel().close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理连接请求
     *
     * @param selectionKey
     * @throws IOException
     */
    private void handleAcceptable(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * 处理读请求
     *
     * @param selectionKey
     * @throws IOException
     */
    private void handleRead(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        socketChannel.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read = socketChannel.read(byteBuffer);
        if (read > 0) {
            byteBuffer.flip();
            byte[] bs = new byte[byteBuffer.remaining()];
            byteBuffer.get(bs);
            System.out.println("客户端请求: " + new String(bs));
            handleWrite(socketChannel, "响应时间: " + new Date().toLocaleString());
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
    private void handleWrite(SocketChannel socketChannel, String content) throws IOException {
        if (content == null) content = "";
        socketChannel.write(ByteBuffer.wrap(content.getBytes()));
    }
}
