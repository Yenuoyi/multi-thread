package semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author Created by yebing
 * @Date 2018/8/29 20:34
 * @Version 1.0.0
 */
public class Demo {
	public static void main(String[] args){
		//这个意思是最多可以同时跑5个线程，其他线程必须等待这几个线程跑完才可以继续
		Semaphore semaphore = new Semaphore(5);
		//创建缓存线程池
		ExecutorService executor = Executors.newCachedThreadPool();
		for(int i=0;i<10;i++){
			executor.execute(new Run(semaphore,i));
		}
		//关闭线程池
		executor.shutdown();
	}
}
