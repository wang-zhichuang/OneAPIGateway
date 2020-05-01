package cn.oneseek.core.nettyserver.requesthandle;

import io.netty.handler.codec.http.FullHttpRequest;

import java.util.HashMap;
import java.util.Map;

public class HandleUrl {
    public static String handleUrl(FullHttpRequest req){
        // 获取请求的uri
        String uri = req.uri();
        Map<String,String> resMap = new HashMap<>();
        resMap.put("method",req.method().name());
        resMap.put("uri",uri);
        String fileName = uri.substring(1);
        return fileName;
    }
}
