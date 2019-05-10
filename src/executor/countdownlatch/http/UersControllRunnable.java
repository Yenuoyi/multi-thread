package executor.countdownlatch.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;

public class UersControllRunnable implements Runnable{

	private static int count1=0;//计算当前线程
	private static int count2=0;//统计已完成多少线程
	private static int success = 0;//成功数
	private static int failure = 0;//失败数
	private String param;//发送的参数
	private CountDownLatch countDownLatch;//倒计时器
	private int ReadTimeout;//读取流超时时间
	public UersControllRunnable(String param,CountDownLatch countDownLatch,int ReadTimeout){
		this.param = param;
		this.countDownLatch = countDownLatch;
		this.ReadTimeout = ReadTimeout;
	}
	//构造函数重载
	public UersControllRunnable(){
		
	}
	@Override
	public void run() {
		int num;
		++count1;
		num = count1;
		int response = 0;
		long startTime = System.currentTimeMillis();
		SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
		PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL("http://119.23.51.148:8080/BBS/Welcome");
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
          //阅读是否超时
            conn.setReadTimeout(ReadTimeout);
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲,通过flush方法将这个缓冲区里的数据一起发送出去
            out.flush();
            //连接状态码
            response = conn.getResponseCode();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line+"\n";
            }
            ++success;
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            ++failure;
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        ++count2;
        //输出返回的页面代码
        System.out.println(result);
        System.out.println("线程"+num+"     开始时间："+ft.format(startTime)+"  结束时间:"+ft.format(endTime) +"运行时间："+(endTime-startTime)+
        		"    响应状态:"+response+"   当前已完成线程数为："+count2+"   成功线程："+success+"   失败线程："+failure);
        countDownLatch.countDown();
	}
	public String getResult(){
		String result = "成功数："+success+"   失败数："+failure;
		return result;
	}
}
