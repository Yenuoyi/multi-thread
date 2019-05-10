package callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * @Author Created by yebing
 * @Date 2018/8/29 20:34
 * @Version 1.0.0
 */
public class Demo {
	public static void main(String[] args) throws InterruptedException, ExecutionException{
		//创建个线程数固定为10的线程池
		ExecutorService executors = Executors.newFixedThreadPool(10);
		//循环加入线程
		for(int i=0;i<10;i++){
			//Future获取线程返回结果
			Future<String> future = executors.submit(new CallableImpl());
			System.out.println(future.get());
		}
		//老规矩，线程池切记要关了，不然这个会一直跑下去
		executors.shutdown();
	}
}
