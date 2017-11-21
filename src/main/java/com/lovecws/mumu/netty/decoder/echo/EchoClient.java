package com.lovecws.mumu.netty.decoder.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: echo客户端
 * @date 2017-11-20 10:22
 */
public class EchoClient {
    public static void main(String[] args) {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup)
                .option(ChannelOption.TCP_NODELAY, true)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(final SocketChannel socketChannel) throws Exception {
                        if (EchoServer.NETTY_DELIMITER.equalsIgnoreCase("\n")) {
                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        } else if (EchoServer.NETTY_DELIMITER.isEmpty()) {
                            socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(1024));
                        } else {
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(EchoServer.NETTY_DELIMITER.getBytes())));
                        }
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new EchoClientChannelHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect(EchoServer.NETTY_HOST, EchoServer.NETTY_PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }

    public static class EchoClientChannelHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(final ChannelHandlerContext ctx) throws Exception {
            for (int i = 0; i < 100; i++) {
                ctx.writeAndFlush(Unpooled.copiedBuffer(("lovecws" + EchoServer.NETTY_DELIMITER).getBytes()));
            }
        }

        @Override
        public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
            if (msg instanceof String) {
                System.out.println("echoclient receive message:" + msg);
            }
        }

        @Override
        public void channelReadComplete(final ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
            ctx.close();
        }
    }
}
