package condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Created by yebing
 * @Date 2018/8/29 20:34
 * @Version 1.0.0
 */
public class ConditionLock {
    //可重入锁
	final Lock lock = new ReentrantLock();
    //获取condition
    final Condition notFull  = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    //ConditionLock对象数组最多存放个object
    final Object[] items = new Object[5];
    //存入数组地址
    private int putptr;
    //取出数字地址
    private int takeptr;
    //Object数组库存数
    private int count;

    public void put(Object x){
        lock.lock();
        try {
            if (count == items.length){
                //如果当前数组已满，则导致当前线程等到发信号或 interrupted 。
                notFull.await();
            }
            items[putptr] = x;
            if (++putptr == items.length){
                //表示数组已满，putptr地址回归0
                putptr = 0;
            }
            ++count;
            //通知take（）可以继续获取数组对象啦
            notEmpty.signal();
            System.out.println(Thread.currentThread().getName() + " put  "+ (Integer)x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void take(){
        lock.lock();
        try {
            if (count == 0){
                notEmpty.await();
            }
            Object x = items[takeptr];
            if (++takeptr == items.length){
                takeptr = 0;
            }
            --count;
            notFull.signal();
            System.out.println(Thread.currentThread().getName() + " take "+ (Integer)x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
