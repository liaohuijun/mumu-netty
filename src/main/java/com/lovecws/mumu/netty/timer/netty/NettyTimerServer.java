package com.lovecws.mumu.netty.timer.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: netto开发时间服务器
 * @date 2017-11-17 17:00
 */
public class NettyTimerServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChildrenChannelHandler());
        try {
            ChannelFuture future = serverBootstrap.bind(9999).sync();
            System.out.println("server starting......................");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static class ChildrenChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(final SocketChannel channel) throws Exception {
            //netty支持粘包
            channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            channel.pipeline().addLast(new StringDecoder());
            channel.pipeline().addLast(new TimerServerHandler());
        }
    }

    public static class TimerServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
            if (msg instanceof ByteBuf) {
                ByteBuf byteBuf = (ByteBuf) msg;
                byte[] bs = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes(bs);
                System.out.println("netty timer server receive msg:" + new String(bs));
            } else if (msg instanceof String) {
                System.out.println("netty timer server receive msg:" + msg);
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer(("lovecws"+System.getProperty("line.separator")).getBytes()));
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
