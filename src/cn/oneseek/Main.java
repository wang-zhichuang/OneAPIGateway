package cn.oneseek;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Handler;

/**
 * @Author: Chuang
 * @Date: 2020/3/28 20:04
 */
public class Main {
    public static void main(String[] args) {
        HttpService httpService = new HttpService();
        httpService.run();
    }


}
