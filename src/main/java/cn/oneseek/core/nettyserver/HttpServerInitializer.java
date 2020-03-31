package cn.oneseek.core.nettyserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author: Chuang
 * @Date: 2020/3/31 1:57
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //channel 代表了一个socket.
        ChannelPipeline pipeline = channel.pipeline(); // ChannelPipeline一串handler.
        pipeline.addLast(new HttpServerCodec());// http消息的编解码器
        pipeline.addLast("httpAggregator",new HttpObjectAggregator(512*1024)); // http 消息聚合器                                                                     512*1024为接收的最大contentlength
        pipeline.addLast(new HttpRequestHandler());// 请求处理器

    }
}
