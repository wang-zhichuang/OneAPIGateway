package cn.oneseek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * @Author: Chuang
 * @Date: 2020/3/28 23:16
 */
public class HttpService {
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
    public static void service(Socket socket)
    {
        new Thread(() -> {
            try {
                /*
                 * 接受HTTP请求，并解析数据
                 */
                String requestHeader;
                List<String> res = null;
                BufferedReader bd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                requestHeader = bd.readLine();
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
    public static List<String> handleService(String requestHeader){
        String fileName;
        String shellPath = "./";
        int end = requestHeader.indexOf("HTTP/");
        fileName = requestHeader.substring(5, end-1); // 得到请求参数
//        System.out.println(shellPath+"\t"+fileName);
        IOTools ioTools = new IOTools();
        if(!fileName.equals("favicon.ico")){
            ioTools.createFile(shellPath+"test/",fileName);
        }
        String shell = shellPath + "test.sh " + shellPath +"test/"+ fileName;
        List<String> statInfo = ioTools.runStat(shell);
//        System.out.println(shell);
        return statInfo;
    }
}
