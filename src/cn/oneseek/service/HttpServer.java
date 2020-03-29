package cn.oneseek.service;

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
    private ShellTools shellTools = new ShellTools();
    private IOTools ioTools = new IOTools();
    public void run(){
        try {
            /*监听端口8888*/
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("正在运行：8888");
            while (true) {
                Socket socket = serverSocket.accept();
                service(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {

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
                    res = handleService(requestHeader);
                    System.out.println("处理请求...");
                }

                PrintWriter pw = new PrintWriter(socket.getOutputStream());

                pw.println("HTTP/1.1 200 OK");
                pw.println("Content-type:text/html;charset=utf-8");
                pw.println();
                pw.println(res);

                pw.flush();
                System.out.println("返回信息");
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    private List<String> handleService(String requestHeader){
        int end = requestHeader.indexOf("HTTP/");
        String fileName = requestHeader.substring(5, end-1); // 得到请求参数
        String shellPath = "./";

        if(!fileName.equals("favicon.ico")){ //会有一个不需要的请求"favicon.ico"
            boolean createFileIsSuccess = ioTools.createFile(shellPath+"test/",fileName);
            System.out.println("创建文件："+fileName+"成功："+createFileIsSuccess);
        }
        String shell = shellPath + "test.sh " + shellPath +"test/"+ fileName;
        List<String> statInfo = new ArrayList<>();
        statInfo = shellTools.runStat(shell);;

        return statInfo;
    }
}