package reentrant.readwriter.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

/**
 * @Author Created by yebing
 * @Date 2018/8/29 20:34
 * @Version 1.0.0
 */
public class ReadLockRun implements Runnable{
	private ReadLock lock;
	private String name;
	private ObjectPojo objectPojo;
	public ReadLockRun(ReadLock readLock, String name, ObjectPojo objectPojo){
		this.lock = readLock;
		this.name = name;
		this.objectPojo = objectPojo;
	}
	@Override
	public void run() {
		lock.lock();//获取读锁
		System.out.println(name+"查询对象数据：年龄:"+ objectPojo.getAge()+"  性别"+ objectPojo.getSex());
		try {
			//线程检测效果输出
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("--------------");
		lock.unlock();//必须释放锁
	}

	
}
