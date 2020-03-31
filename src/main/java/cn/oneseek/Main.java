package cn.oneseek;

//import cn.oneseek.core.server.HttpServer;
import cn.oneseek.core.server.Server;
import cn.oneseek.core.nettyserver.HttpServer;
/**
 * @Author: Chuang
 * @Date: 2020/3/28 20:04
 */
public class Main {
    public static void main(String[] args)  {
//        Server httpServer = new HttpServer();
//        httpServer.run();
        HttpServer httpServer = new HttpServer(8888);
        try {
            httpServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
