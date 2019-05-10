package forkandjoin;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RecursiveAction;

/**
 * @author yebing
 * 继承RecurSiveAction
 * 此类用于分解任务
 * 这个类我感觉更像递归
 */
public class RecurSiveActionImpl extends RecursiveAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int startValue;
	private int endValue;
	private CountDownLatch count;
	public RecurSiveActionImpl(int startValue, int endValue, CountDownLatch count){
		this.startValue = startValue;
		this.endValue = endValue;
		this.count = count;
	}
	public RecurSiveActionImpl(int startValue, int endValue){
		this.startValue = startValue;
		this.endValue = endValue;
	}
	@Override
	protected void compute() {
		//输出当前线程名
		System.out.println(Thread.currentThread().getName());
		if(endValue - startValue>2){
			int mid = (endValue+startValue)/2;
			RecurSiveActionImpl left = new RecurSiveActionImpl(startValue,mid);
			RecurSiveActionImpl right = new RecurSiveActionImpl(mid+1,endValue);
			RecurSiveActionImpl.invokeAll(left,right);
		}else{
			System.out.println("输出分治段:开始"+startValue+" 结束："+endValue);
		}
		//因为存在两个构造方法，一个是拥有count，一个是没有，拥有count的为原始的构造方法，
		//用于执行完分解任务返回后告诉主线程我执行完毕了。
		if(count!=null){
			count.countDown();
			System.out.println(count.getCount());
		}
	}

}
