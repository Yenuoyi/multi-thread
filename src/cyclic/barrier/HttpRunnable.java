package cyclic.barrier;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 并发线程，线程数必须达到一定数量才进行
 * @author yebing
 */
public class HttpRunnable implements Runnable{
	private CyclicBarrier cyclic;
	private int timeout;
	private int readTimeout;
	public HttpRunnable(CyclicBarrier cyclic, int timeout, int readTimeout){
		this.cyclic = cyclic;
		this.timeout = timeout;
		this.readTimeout = readTimeout;
	}
	@Override
	public void run() {
		try{
			//等待达到指定并发数，或者超时
			System.out.println("等待线程到达并发数！！！");
			cyclic.await(timeout, TimeUnit.SECONDS);
			System.out.println("并发数已到达，开始执行！");
			//获取连接
			URL url = new URL("http://119.23.51.148:8080/BBS/Hello");
			//打开连接
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			//设置读取超时
			conn.setReadTimeout(readTimeout);
			 // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //获取返回的页面
            InputStream input = conn.getInputStream();
            String result = "";
            byte[] by = new byte[1024];
            //从input中读取数据至byte数组，读取完毕，next就为-1；
            int next = input.read(by);
            while(next>-1){
            	result = result+new String(by);
            	next = input.read(by);
            }
            //获取返回的状态码
            int response = conn.getResponseCode();
            System.out.println("状态码："+response);
//            System.out.println("结果集："+"\n"+result);
            //将屏障重置为初始状态。 如果任何一方正在等待屏障，他们将返回BrokenBarrierException 。 
            //注意，由于其他原因，发生断线后的复位可能会复杂化; 线程需要以其他方式重新同步，并选择一个执行重置。
            //可能更好地为后续使用创建新的屏障。 
            cyclic.reset();
		}catch (InterruptedException e) {
            e.printStackTrace();
        }catch(BrokenBarrierException e){
            e.printStackTrace();
        }catch (IOException e){
        	e.printStackTrace();
        }catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}
