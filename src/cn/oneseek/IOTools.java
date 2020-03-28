package cn.oneseek;

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
class IOTools {
    boolean createFile(String filePath,String fileName) {
        try {
            File dir=new File(filePath);
            if(!dir.exists()){
                dir.mkdir();
            }

            File file = new File(filePath+fileName);
            if (file.exists()) {
                file.delete();
                return file.createNewFile();
            } else {
                return file.createNewFile();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }

    public List<String> runStat(String shellPath) {
        List<String> strList = new ArrayList<String>();
        try {
            Process process = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", shellPath }, null, null);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            process.waitFor();
            while ((line = input.readLine()) != null) {
                strList.add(line);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return strList;
    }
}
