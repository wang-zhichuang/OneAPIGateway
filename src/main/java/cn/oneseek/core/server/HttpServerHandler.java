package cn.oneseek.core.server;

import cn.oneseek.tools.IOTools;
import cn.oneseek.tools.ShellTools;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Chuang
 * @Date: 2020/3/31 9:55
 */
public class HttpServerHandler {
    private ShellTools shellTools = new ShellTools();
    private IOTools ioTools = new IOTools();
    List<String> handleService(String requestHeader){
        int end = requestHeader.indexOf("HTTP/");
        String fileName = requestHeader.substring(5, end-1); // 得到请求参数
        String shellPath = "./";

        if(!fileName.equals("favicon.ico")){ //会有一个不需要的请求"favicon.ico"
            boolean createFileIsSuccess = ioTools.createFile(shellPath+"test/",fileName);
            System.out.println("创建文件："+fileName+"成功："+createFileIsSuccess);
        }
        String shell = shellPath + "test.sh " + shellPath +"test/"+ fileName;
        List<String> statInfo = new ArrayList<>();
        statInfo = shellTools.runShell(shell); // 运行shell命令 得到文件信息

        return statInfo;
    }
}
