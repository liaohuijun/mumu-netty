package com.lovecws.mumu.netty.serialization.protobuf;

import com.lovecws.mumu.netty.serialization.protobuf.news.SinaFinanceNews;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: protobuf序列化服务器
 * @date 2017-11-20 15:36
 */
public class ProtobufServer {
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
                        //protobuf序列化工具
                        socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                        socketChannel.pipeline().addLast(new ProtobufDecoder(SinaFinanceNews.getDefaultInstance()));
                        socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                        socketChannel.pipeline().addLast(new ProtobufEncoder());

                        socketChannel.pipeline().addLast(new ProtobufServerChannelHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(ProtobufServer.NETTY_HOST, ProtobufServer.NETTY_PORT).sync();
            System.out.println("ProtobufServer starting........................");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static class ProtobufServerChannelHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
            System.out.println("ProtobufServer receive message:" + msg + "  " + counter.incrementAndGet());

            //回复
            SinaFinanceNews.Builder builder = SinaFinanceNews.newBuilder();
            builder.setHtitle("babymm");
            builder.setKeywords("爱好");
            builder.setCategory("科技");
            builder.setContent("lovecwslsllslsla");
            builder.setDescription("描述信息");
            builder.setLogo("lslslsls");
            builder.setMediaName("小墨科技");
            builder.setUrl("");
            builder.setType("华四金融");
            SinaFinanceNews sinaFinanceNews = builder.build();
            ctx.writeAndFlush(sinaFinanceNews);
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
