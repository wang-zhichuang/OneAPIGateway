package cn.oneseek;

import cn.oneseek.service.HttpServer;
import cn.oneseek.service.Server;

/**
 * @Author: Chuang
 * @Date: 2020/3/28 20:04
 */
public class Main {
    public static void main(String[] args) {
        Server httpServer = new HttpServer();
        httpServer.run();
    }


}
