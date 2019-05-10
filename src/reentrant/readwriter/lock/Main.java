package reentrant.readwriter.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author Created by yebing
 * @Date 2018/8/29 20:34
 * @Version 1.0.0
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		ObjectPojo objectPojo = new ObjectPojo();
		objectPojo.setAge("20");
		objectPojo.setSex("man");
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();//可重入读写锁
		ExecutorService executor = Executors.newCachedThreadPool();//线程池
		//ReentrantReadWriteLock.ReadLock readLock(),返回用于阅读的锁。
		//ReentrantReadWriteLock.WriteLock writeLock().返回用于写入的锁。
		executor.execute(new WriterLockRun(lock.writeLock(),"user0",objectPojo));
		executor.execute(new ReadLockRun(lock.readLock(),"user1", objectPojo));
		executor.execute(new ReadLockRun(lock.readLock(),"user2", objectPojo));
		executor.execute(new WriterLockRun(lock.writeLock(),"user3", objectPojo));
		executor.execute(new ReadLockRun(lock.readLock(),"user4", objectPojo));
		executor.execute(new WriterLockRun(lock.writeLock(),"user5", objectPojo));
		executor.shutdown();
	}
}
