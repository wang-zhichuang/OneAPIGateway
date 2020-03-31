package cn.oneseek.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Chuang
 * @Date: 2020/3/28 16:22
 */
public class IOTools {
    public boolean createFile(String filePath, String fileName) {
        try {
            File dir=new File(filePath);
            if(!dir.isDirectory()){ //不是文件夹删掉创建一个文件夹
                dir.delete();
            }
            boolean mkdirIsSuccess = dir.mkdir();
//          System.out.println("新建文件夹"+fileName+":"+mkdirIsSuccess);


            File file = new File(filePath+fileName);
            if (file.exists()) {
                boolean deleteIsSuccess = file.delete();
//                System.out.println("删除已存在的文件"+fileName+":"+deleteIsSuccess);
                return file.createNewFile();
            } else {
                return file.createNewFile();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }
}
