package com.lovecws.mumu.netty.protocol.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: websocket
 * @date 2017-11-21 11:07
 */
public class WebsocketServer {
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
                        socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());
                        socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65535));
                        socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                        socketChannel.pipeline().addLast("WebsocketServerChannelHandler", new WebsocketServerChannelHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind("localhost", 8888).sync();
            System.out.println("WebsocketServer starting.........");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static class WebsocketServerChannelHandler extends SimpleChannelInboundHandler {
        @Override
        protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final Object msg) throws Exception {
            if (msg instanceof FullHttpRequest) {
                FullHttpRequest request = (FullHttpRequest) msg;
                if (!request.decoderResult().isSuccess() || !"websocket".equalsIgnoreCase(request.headers().get("Upgrade"))) {
                    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
                    response.headers().set("content-type", "text/plan; charset=utf-8");
                    channelHandlerContext.writeAndFlush(response);
                    return;
                }
                //websocket握手
                WebSocketServerHandshakerFactory handshakerFactory = new WebSocketServerHandshakerFactory("ws://localhost:8888/websocket", null, false);
                WebSocketServerHandshaker webSocketServerHandshaker = handshakerFactory.newHandshaker(request);
                if (webSocketServerHandshaker == null) {
                    WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channelHandlerContext.channel());
                } else {
                    webSocketServerHandshaker.handshake(channelHandlerContext.channel(), request);
                }
            } else if (msg instanceof WebSocketFrame) {
                WebSocketFrame webSocketFrame = (WebSocketFrame) msg;
                if (webSocketFrame instanceof CloseWebSocketFrame) {
                    webSocketFrame.retain();
                    return;
                }
                if (webSocketFrame instanceof PingWebSocketFrame) {
                    channelHandlerContext.channel().write(new PongWebSocketFrame(webSocketFrame.content().retain()));
                    return;
                }
                if (webSocketFrame instanceof TextWebSocketFrame) {
                    String request = ((TextWebSocketFrame) webSocketFrame).text();
                    channelHandlerContext.channel().write(new TextWebSocketFrame(request + "欢迎使用 netty websocket,现在时刻:" + new Date().toLocaleString()));
                }
            }
        }

        @Override
        public void channelReadComplete(final ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }
    }
}
