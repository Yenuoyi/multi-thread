package callable;

import java.util.Random;
import java.util.concurrent.Callable;
/**
 * @Author Created by yebing
 * @Date 2018/8/29 20:34
 * @Version 1.0.0
 */
public class CallableImpl implements Callable<String>{
	private ThreadLocal<Integer> id = new ThreadLocal<Integer>();
	@Override
	public String call() throws Exception {
		Random ran = new Random();
		id.set(ran.nextInt(50));
		return "线程id:"+id.get()+"执行完毕!";
	}

}
