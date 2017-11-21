package com.lovecws.mumu.netty.protocol.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: upd服务器
 * @date 2017-11-21 13:36
 */
public class UDPServer {
    public static final int UPD_PORT = 7777;

    public static void main(String[] args) {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                    @Override
                    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final DatagramPacket packet) throws Exception {
                        System.out.println(packet.content().toString(CharsetUtil.UTF_8));
                        channelHandlerContext.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(("lovecws5211314" + new Date().toLocaleString()),CharsetUtil.UTF_8), packet.sender()));
                    }

                    @Override
                    public void channelReadComplete(final ChannelHandlerContext ctx) throws Exception {
                        ctx.flush();
                    }

                    @Override
                    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
                        ctx.close();
                        cause.printStackTrace();
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.bind(UPD_PORT).sync();
            System.out.println("UDPServer starting.........");
            channelFuture.channel().closeFuture().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }
}
