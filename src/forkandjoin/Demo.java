package forkandjoin;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

/**
 * @author yebing
 * 个人感觉这个分治线程更适合用在alibaba对象存储分片上传上，有兴趣的可以自己尝试一下。
 * 实现方法就像下面一般，如果你看见了，觉得不好可以联系我哦！我会很感谢你在我学习的路上对我的帮助。
 */
public class Demo {
	public static void main(String[] args) throws InterruptedException, ExecutionException{
		CountDownLatch count = new CountDownLatch(1);
		ForkJoinPool pool = new ForkJoinPool();
		pool.submit(new RecurSiveActionImpl(0,10,count));
		//等待count为0,这样子就不怕主线程不等待子线程结束就over了
		count.await();
	}
}
