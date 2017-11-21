package com.lovecws.mumu.netty.decoder.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: echo服务端
 * @date 2017-11-20 10:12
 */
public class EchoServer {

    public static final String NETTY_DELIMITER = "$_";
    public static final String NETTY_HOST = "localhost";
    public static final int NETTY_PORT = 9999;

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        serverBootstrap = serverBootstrap
                .group(bossGroup, workGroup)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(final SocketChannel channel) throws Exception {
                        if (NETTY_DELIMITER.equalsIgnoreCase("\n")) {
                            channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        } else if (EchoServer.NETTY_DELIMITER.isEmpty()) {
                            channel.pipeline().addLast(new FixedLengthFrameDecoder(1024));
                        } else {
                            channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(NETTY_DELIMITER.getBytes())));
                        }
                        channel.pipeline().addLast(new StringDecoder());
                        channel.pipeline().addLast(new EchoServerChannelHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(NETTY_HOST, NETTY_PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static class EchoServerChannelHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
            if (msg instanceof String) {
                System.out.println("echoserver receive message:" + msg);
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer(("lovercws" + NETTY_DELIMITER).getBytes()));
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
