package com.xw.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xw
 * @date 2019/6/27 18:07
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 实现在任意一个客服端向服务端发送消息，服务端和所有已连接客户端都能接收此消息。（广播） 通道组
    // 保存每个客户端对服务器的链接
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    // 时间格式化
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 当Channel中有新的事件消息会自动调用
     * @param channelHandlerContext
     * @param textWebSocketFrame
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        // 当前收到数据后会自动调用

        // 获取客户端发送过来的文本消息 特殊字符？ 表情？图片？视频？文件？压缩包？
        String text = textWebSocketFrame.text();
        System.out.println("接收到的消息是："+text);

        for (Channel client : clients) {
            // 将消息发送到所有的客户端
            client.writeAndFlush(new TextWebSocketFrame(sdf.format(new Date()) + "：" + text));
        }

    }

    /**
     * 当有新的客户端链接服务器时会自动调用这个方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
       // 将新的通道放进clients
        clients.add(ctx.channel());
    }
}
