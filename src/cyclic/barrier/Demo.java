package cyclic.barrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {
	public static void main(String[] args){
		//创建等待并发数
		CyclicBarrier cyclic = new CyclicBarrier(5);
		//创建线程池
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for(int i=0;i<10;i++){
			executor.execute(new HttpRunnable(cyclic,10,1000));
		}
		//关闭线程池，否则将持续等待
		executor.shutdown();
	}
}
