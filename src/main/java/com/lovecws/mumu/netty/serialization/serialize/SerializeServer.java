package com.lovecws.mumu.netty.serialization.serialize;

import com.lovecws.mumu.netty.serialization.UserInfo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: java 序列化服务器
 * @date 2017-11-20 12:19
 */
public class SerializeServer {

    public static final String NETTY_DELIMITER = "$_";
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
                        //java序列化解码 编码
                        socketChannel.pipeline().addLast(new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingConcurrentResolver(SerializeServer.class.getClassLoader())));
                        socketChannel.pipeline().addLast(new ObjectEncoder());

                        //socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        //socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new SerializeServerChannelHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(SerializeServer.NETTY_HOST, SerializeServer.NETTY_PORT).sync();
            System.out.println("SerializeServer starting........................");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static class SerializeServerChannelHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
            System.out.println("SerializeServer receive message:" + msg + "  " + counter.incrementAndGet());
            ctx.writeAndFlush(new UserInfo(counter.get(), "babymm", "123456"));
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
