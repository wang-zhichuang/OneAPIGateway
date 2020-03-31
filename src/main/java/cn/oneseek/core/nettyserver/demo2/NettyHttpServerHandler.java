package cn.oneseek.core.nettyserver.demo2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

/**
 * @Author: Chuang
 * @Date: 2020/3/31 9:43
 */
public class NettyHttpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer("欢迎来到猿天地".getBytes("utf-8")));
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain;charset=UTF-8");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        ctx.write(response);
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }

}
