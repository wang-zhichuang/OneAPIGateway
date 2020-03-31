# APIGateway

[APIGateway.rar ](https://raw.githubusercontent.com/OneSeek/APIGateway/master/版本/APIGateway-0.0.1.rar)

# 1.下载APIGateway.rar 
# 2.解压，进入解压目录 
# 3.给test.sh执行权限，
# 4.java -jar APIGateway.jar


# 后台运行，输出日志到log文件，运行方法
将 startServer.sh、test.sh、APIGateway.jar放在同级目录下，给两个shell脚本执行权限，直接运行startServer.sh即可

[startServer.sh](https://raw.githubusercontent.com/OneSeek/APIGateway/master/startService.sh)
[test.sh](https://raw.githubusercontent.com/OneSeek/APIGateway/master/test.sh)
[APIGateway.jar](https://raw.githubusercontent.com/OneSeek/APIGateway/master/APIGateway.jar)

也可以只下载 test.sh、APIGateway.jar 然后运行 APIGateway.jar 

# 待扩展
- [ ] 给log日志加时间戳，每次运行生成一个log文件
- [ ] 提高性能NIO、AIO实现Server类
- [ ] 使用netty


