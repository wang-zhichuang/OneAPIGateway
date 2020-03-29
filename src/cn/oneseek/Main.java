package cn.oneseek;

import cn.oneseek.service.HttpService;
import cn.oneseek.service.Service;

/**
 * @Author: Chuang
 * @Date: 2020/3/28 20:04
 */
public class Main {
    public static void main(String[] args) {
        Service httpService = new HttpService();
        httpService.run();
    }


}
