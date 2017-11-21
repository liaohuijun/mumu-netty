package com.lovecws.mumu.netty.protocol.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: http文件服务器
 * @date 2017-11-21 8:56
 */
public class HttpFileServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(final SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                        socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65535));
                        socketChannel.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                        socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                        socketChannel.pipeline().addLast("fileServerHandler", new HttpFileServerChannelHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();
            System.out.println("HttpFileServer starting.........");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static class HttpFileServerChannelHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
        @Override
        protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final FullHttpRequest request) throws Exception {
            String uri = request.uri();
            System.out.println(uri);
            if (!request.decoderResult().isSuccess()) {
                sendError(channelHandlerContext, HttpResponseStatus.BAD_REQUEST);
                return;
            }
            if (!request.method().name().equalsIgnoreCase("GET")) {
                sendError(channelHandlerContext, HttpResponseStatus.FORBIDDEN);
                return;
            }
            String path = url(uri);
            File file = new File(path);
            if (!file.exists() || file.isHidden()) {
                sendError(channelHandlerContext, HttpResponseStatus.NOT_FOUND);
                return;
            }
            if (file.isDirectory()) {
                sendListing(channelHandlerContext, file, uri);
                return;
            }
            sendFile(channelHandlerContext, file);
        }
    }

    private static String url(String uri) {
        String path = "D:/data/webmagic";
        if (!uri.equalsIgnoreCase("/")) {
            path = path + uri;
        }
        return path;
    }

    private static void sendListing(ChannelHandlerContext context, File file, String uri) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set("content-type", "text/html; charset=utf-8");

        if (uri.equalsIgnoreCase("/")) uri = "";
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html>\r\n");
        builder.append("<html>\r\n");
        builder.append("<head>\r\n");
        builder.append("<title>" + file.getPath() + "目录:</title>\r\n");
        builder.append("</head>\r\n");
        builder.append("<body>\r\n");
        builder.append("\t<h3>" + file.getPath() + "目录: </h3>\r\n");
        builder.append("\t<ul style=\"list-style-type:none\">\r\n");
        builder.append("\t\t<li><a href=\"." + uri + "\"> . </a></li>\r\n");
        builder.append("\t\t<li><a href=\"../\"> .. </a></li>\r\n");
        for (File f : file.listFiles()) {
            if (f.isHidden() || !f.canRead()) {
                continue;
            }
            String name = f.getName();
            builder.append("\t\t<li><a href=\"" + uri + "/" + name + "\">" + name + "</a></li>\r\n");
        }
        builder.append("\t</ul>\r\n");
        builder.append("</body>\r\n");
        builder.append("</html>\r\n");
        ByteBuf byteBuf = Unpooled.copiedBuffer(builder, CharsetUtil.UTF_8);
        response.content().writeBytes(byteBuf);
        byteBuf.release();
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendFile(ChannelHandlerContext context, File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        setContentTypeHeader(response, file);
        response.headers().set("content-length", randomAccessFile.length());
        byte[] bytes = new byte[1024];
        int read = -1;
        while ((read = randomAccessFile.read(bytes)) > -1) {
            response.content().writeBytes(bytes);
        }

        context.write(response);
        ChannelFuture lastChannelFuture = context.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        lastChannelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendRedirect(ChannelHandlerContext context, String uri) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        response.headers().set("location", uri);
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendError(ChannelHandlerContext context, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(("failure:" + status.toString()).getBytes()));
        response.headers().set("content-type", "text/plan; charset=utf-8");
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void setContentTypeHeader(HttpResponse response, File file) {
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        response.headers().set("content-type", mimetypesFileTypeMap.getContentType(file));
    }

}
