package com.xw.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author xw
 * @date 2019/6/27 17:26
 */
public class WebSocketNettyServer {
    public static void main(String[] args) {
        NioEventLoopGroup mainGroup = new NioEventLoopGroup(); // 主线程池
        NioEventLoopGroup supGroup = new NioEventLoopGroup(); // 子线程池

        try {
            // 创建netty服务器启动对象
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            // 初始化netty启动对象
            serverBootstrap
                    // 指定使用的主从线程池
                    .group(mainGroup,supGroup)
                    // 指定netty通道类型 Nio
                    .channel(NioServerSocketChannel.class)
                    // 指定通道初始化器用来加载通道收到消息后，如何进行业务处理
                    .childHandler(new WebsocketChannelInitializer())
            ;

            // 绑定服务器端口启动服务器
            ChannelFuture future = serverBootstrap.bind(9090).sync();
            //等待服务器关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭服务器
            mainGroup.shutdownGracefully();
            supGroup.shutdownGracefully();
        }
    }
}
