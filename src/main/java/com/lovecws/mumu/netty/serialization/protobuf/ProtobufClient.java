package com.lovecws.mumu.netty.serialization.protobuf;

import com.lovecws.mumu.netty.serialization.protobuf.news.SinaFinanceNews;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: protobuf客户端
 * @date 2017-11-20 15:38
 */
public class ProtobufClient {
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
                        //protobuf序列化
                        socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                        socketChannel.pipeline().addLast(new ProtobufDecoder(SinaFinanceNews.getDefaultInstance()));
                        socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                        socketChannel.pipeline().addLast(new ProtobufEncoder());

                        socketChannel.pipeline().addLast(new ProtobufClientChannelHandler());
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

    public static class ProtobufClientChannelHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(final ChannelHandlerContext ctx) throws Exception {
            for (int i = 0; i < 100; i++) {
                SinaFinanceNews.Builder builder = SinaFinanceNews.newBuilder();
                builder.setHtitle("babymm123");
                builder.setKeywords("爱好123");
                builder.setCategory("科技11");
                builder.setContent("lovecwslsllsl111sla");
                builder.setDescription("描述信息234");
                builder.setLogo("lslslsl12s");
                builder.setMediaName("小墨112科技");
                builder.setUrl("123");
                builder.setType("华四金融123");
                SinaFinanceNews sinaFinanceNews = builder.build();
                ctx.write(sinaFinanceNews);
            }
            ctx.flush();
        }

        @Override
        public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
            System.out.println("ProtobufClient receive message:" + msg);
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
