package cn.oneseek.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AioServer implements Runnable
{

	private AsynchronousChannelGroup asyncChannelGroup;
	private AsynchronousServerSocketChannel server;
 
	public AioServer(int port) throws Exception
	{
		// 创建线程池
		ExecutorService executor = Executors.newFixedThreadPool(20);
		// 异步通道管理器
		asyncChannelGroup = AsynchronousChannelGroup.withThreadPool(executor);
		// 创建 用在服务端的异步Socket.以下简称服务器socket。
		// 异步通道管理器，会把服务端所用到的相关参数
		server = AsynchronousServerSocketChannel.open(asyncChannelGroup).bind(
				new InetSocketAddress(port));
	}
 
	public void run()
	{
		try
		{
 
			// 为服务端socket指定接收操作对象.accept原型是：
			// accept(A attachment, CompletionHandler<AsynchronousSocketChannel,
			// ? super A> handler)
			// 也就是这里的CompletionHandler的A型参数是实际调用accept方法的第一个参数
			// 即是listener。另一个参数V，就是原型中的客户端socket
			server.accept(
					server,
					new CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>()
					{
						String str = "<html><head><title>test</title></head><body><p>this is a socketserver test</p></body></html>";
						String CRLF = "\r\n";
						// 响应头的参数
						String serverLine = "Server:a simple java WebServer";
						String statusLine = "HTTP/1.1 200 OK" + CRLF;
						String contentTypeLine = "Content-type:text/html"
								+ CRLF;
						String contentLengthLine = "Content-Length:" + str.length()
								+ CRLF;
 
 
						@Override
						public void completed(AsynchronousSocketChannel result,
								AsynchronousServerSocketChannel attachment)
						{
							// TODO Auto-generated method stub
							// writeChannel(result, statusLine);
							// writeChannel(result, serverLine);
							// writeChannel(result, contentTypeLine);
							// writeChannel(result, contentLengthLine);
							// writeChannel(result, CRLF);
 
							writeChannel(result, statusLine + serverLine
									+ contentTypeLine + contentLengthLine
									+ CRLF + str);
 
							// writeChannel(result, str);
 
							try
							{
								result.shutdownOutput();
								result.shutdownInput();
							} catch (IOException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
 
							try
							{
								result.close();
							} catch (IOException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
 
							attachment.accept(attachment, this);
 
						}
 
						@Override
						public void failed(Throwable exc,
								AsynchronousServerSocketChannel attachment)
						{
							// TODO Auto-generated method stub
 
						}
 
						public void writeChannel(
								AsynchronousSocketChannel channel, String s)
						{
							Future<Integer> future = channel.write(ByteBuffer
									.wrap(s.getBytes()));
 
							try
							{
								future.get();
							} catch (InterruptedException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
 
					});
			Thread.sleep(400000);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			System.out.println("finished server");
		}
	}
 
	public static void main(String... args) throws Exception
	{
		AioServer server = new AioServer(8888);
		new Thread(server).start();
	}
 
}