package thread.group;

/**
 * @Author Created by yebing
 * @Date 2018/8/29 20:34
 * @Version 1.0.0
 */
public class SynchronizedLock {
	private static long result = 0;
	private static Object object = new Object();
	public static class PlusTask implements Runnable{
		@Override
		public void run() {
			for(int j =0;j<100;j++){
				System.out.println("当前数值："+j);
				synchronized(object){
					result++;
				}
			}
		}
		public static void main(String[] args) throws InterruptedException{
			Thread thread1 = new Thread(new PlusTask());
			Thread thread2 = new Thread(new PlusTask());
			thread1.start();
			thread2.start();
			thread1.join();
			thread2.join();
			System.out.println(result);
	}
	}
}
