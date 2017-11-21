package com.lovecws.mumu.netty.serialization.marshalling;

import com.lovecws.mumu.netty.serialization.UserInfo;
import com.lovecws.mumu.netty.serialization.protobuf.ProtobufServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: protobuf客户端
 * @date 2017-11-20 15:38
 */
public class JbossMarshallingClient {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(final SocketChannel socketChannel) throws Exception {
                        //jboss marshalling 序列化
                        socketChannel.pipeline().addLast(JbossMarshallingFactory.decoder());
                        socketChannel.pipeline().addLast(JbossMarshallingFactory.encoder());

                        socketChannel.pipeline().addLast(new JbossMarshallingChannelHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect(ProtobufServer.NETTY_HOST, ProtobufServer.NETTY_PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static class JbossMarshallingChannelHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(final ChannelHandlerContext ctx) throws Exception {
            for (int i = 0; i < 100; i++) {
                ctx.write(new UserInfo(i, "lovecws", "5211314"));
            }
            ctx.flush();
        }

        @Override
        public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
            System.out.println("JbossMarshallingClient receive message:" + msg);
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
