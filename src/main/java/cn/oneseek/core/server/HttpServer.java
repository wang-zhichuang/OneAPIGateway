package cn.oneseek.core.server;

import cn.oneseek.AppConfigUtil;
import cn.oneseek.tools.IOTools;
import cn.oneseek.tools.ShellTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Chuang
 * @Date: 2020/3/28 23:16
 */
public class HttpServer implements Server {
    private ServerSocket serverSocket = null;
    HttpServerHandler httpServerHandler = new HttpServerHandler();
    public void run(){
        try {
            /*监听端口8888*/
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("正在运行：8888");
            while (AppConfigUtil.isRun) {
                Socket socket = serverSocket.accept();
                service(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            serverSocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void service(Socket socket)
    {
        new Thread(() -> {
            try {
                /*
                 * 接受HTTP请求，并解析数据
                 */
                String requestHeader;
                List<String> res = null;
                BufferedReader bd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                requestHeader = bd.readLine(); // 只要 HTTP Request的第一行 读出 请求文件名

                if(requestHeader!=null){
                    res = httpServerHandler.handleService(requestHeader);
//                    System.out.println("处理请求...");
                }

                PrintWriter pw = new PrintWriter(socket.getOutputStream());

                pw.println("HTTP/1.1 200 OK");
                pw.println("Content-type:text/html;charset=utf-8");
                pw.println();
                pw.println(res);

                pw.flush();
//                System.out.println("返回信息");
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}