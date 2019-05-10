package thread.local;


/**
 * @author yebing
 * ThreadLocal并不是为了实现线程共享变量而出现的，相反他是线程局部变量，属于线程自己。
 * ThreadLocal是线程自己私有的，在每个方法体中可以使用get（）去获取他。
 */
public class ThreadLocalExample {

    /**
     * 静态内部类继承了Runnable接口，谨记实现多线程不单止继承Thread和实现Runable，还有callable接口
     */
    public static class MyRunnable implements Runnable {
        private ThreadLocal<Integer> threadLocal =
               new ThreadLocal<Integer>();
        @Override
        public void run() {
            threadLocal.set( (int) (Math.random() * 100D) );
            getThreadLocal();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }finally{
            	//如果线程本地没有通过调用它的remove方法来清除，
            	//它将永远不会允许被垃圾收集，并且最终会有很多未使用的实例。
            	threadLocal.remove();
            }
            System.out.println(threadLocal.get());
        }
        public void getThreadLocal(){
        	System.out.println(threadLocal.get());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyRunnable sharedRunnableInstance = new MyRunnable();
        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);
        thread1.start();
        thread2.start();
        thread1.join(); //wait for thread 1 to terminate
        thread2.join(); //wait for thread 2 to terminate
    }

}
