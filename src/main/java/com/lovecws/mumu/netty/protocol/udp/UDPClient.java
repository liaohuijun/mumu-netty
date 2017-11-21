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

import java.net.InetSocketAddress;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: udp客户端
 * @date 2017-11-21 13:44
 */
public class UDPClient {
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
                        channelHandlerContext.close();
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
            Channel channel = bootstrap.bind(0).sync().channel();
            channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("".getBytes()), new InetSocketAddress("255.255.255.255", UDPServer.UPD_PORT)));
            if (!channel.closeFuture().await(15000)) {
                System.out.println("查询超时");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }
}
