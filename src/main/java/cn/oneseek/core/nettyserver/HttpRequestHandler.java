package cn.oneseek.core.nettyserver;

/**
 * @Author: Chuang
 * @Date: 2020/3/31 0:26
 */
import cn.oneseek.core.nettyserver.requesthandle.HandleUrl;
import cn.oneseek.tools.IOTools;
import cn.oneseek.tools.ShellTools;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.List;


public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {

        String fileName = HandleUrl.handleUrl(req);

        if (!fileName.equals("favicon.ico")){
            String shellPath = "./";
            IOTools ioTools = new IOTools();
            ioTools.createFile("./test/",fileName);

            ShellTools shellTools = new ShellTools();
            String shell = shellPath + "test.sh " + shellPath +"test/"+ fileName;
            List<String> statInfo = new ArrayList<>();
            statInfo = shellTools.runShell(shell); // 运行shell命令 得到文件信息

            String msg = "<html><head><title>test</title></head><body>"+ statInfo+"</body></html>";

            // 创建http响应
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
            // 设置头信息
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");

            // 将html write到客户端
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }
}