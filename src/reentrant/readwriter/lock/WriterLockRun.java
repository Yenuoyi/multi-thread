package reentrant.readwriter.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * @Author Created by yebing
 * @Date 2018/8/29 20:34
 * @Version 1.0.0
 */
public class WriterLockRun implements Runnable{
	private WriteLock lock;
	private String name;
	private ObjectPojo objectPojo;
	public WriterLockRun(WriteLock lock, String name, ObjectPojo objectPojo){
		this.lock = lock;
		this.name = name;
		this.objectPojo = objectPojo;
	}
	@Override
	public void run() {
		lock.lock();//获取写锁
		System.out.println(name+"查询对象数据：年龄:"+ objectPojo.getAge()+"  性别："+ objectPojo.getSex());
		System.out.println(name+"更改对象数据："+"年龄更正：11，性别更正：woman");
		objectPojo.setAge("11");
		objectPojo.setSex("woman");
		System.out.println("--------------");
		try {
			//线程休眠3s，以便检测是否在写锁介入的情况下能够再获取锁
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			//必须释放锁
			lock.unlock();
		}
	}

}
