package com.lovecws.mumu.netty.serialization.marshalling;

import com.lovecws.mumu.netty.serialization.UserInfo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: protobuf序列化服务器
 * @date 2017-11-20 15:36
 */
public class JbossMarshallingServer {
    public static final String NETTY_HOST = "localhost";
    public static final int NETTY_PORT = 9999;

    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(final SocketChannel socketChannel) throws Exception {
                        //jboss marshalling 序列化
                        socketChannel.pipeline().addLast(JbossMarshallingFactory.decoder());
                        socketChannel.pipeline().addLast(JbossMarshallingFactory.encoder());

                        socketChannel.pipeline().addLast(new JbossMarshallingServerChannelHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(JbossMarshallingServer.NETTY_HOST, JbossMarshallingServer.NETTY_PORT).sync();
            System.out.println("JbossMarshallingServer starting........................");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static class JbossMarshallingServerChannelHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
            System.out.println("JbossMarshallingServer receive message:" + msg + "  " + counter.incrementAndGet());
            ctx.writeAndFlush(new UserInfo(2, "lovecws", "5211314"));
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
