package semaphore;

import java.util.concurrent.Semaphore;

/**
 * @Author Created by yebing
 * @Date 2018/8/29 20:34
 * @Version 1.0.0
 */
public class Run implements Runnable{
	private Semaphore semaphore;
	private int id;
	public Run(Semaphore semaphore,int id){
		this.semaphore = semaphore;
		this.id = id;
	}
	@Override
	public void run(){
		//获取可用跑道
		try {
			semaphore.acquire();
			System.out.println("我的id："+id+"   我开始跑咯！！！");
			//跑完啦该释放跑道啦
			//为了感受效果，可以把semaphore.release()注释掉就知道啦
			semaphore.release();
			System.out.println("我跑完啦！");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
