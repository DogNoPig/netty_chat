package com.xw.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 通道初始化器
 * 用来加载通道处理器（channelHandler）
 * @author xw
 * @date 2019/6/27 17:34
 */
public class WebsocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 初始化通道
     * 在这个方法中加载对应的ChannelMandler
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 获取管道，将一个一个的ChannelHandler添加到管道中
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 添加一个http 的编解码器
        pipeline.addLast(new HttpServerCodec());
        // 添加一个支持大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 添加一个聚合器，主要是将HttpMessage 聚合成 FullHttpRequest/Response
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        // 需要指定一个接收请求的路由
        // 指定以ws为后缀的的URL请求 才能访问
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 添加自定义的handler
        pipeline.addLast(new ChatHandler());

    }
}
