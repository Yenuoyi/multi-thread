package condition;

/**
 * @Author Created by yebing
 * @Date 2018/8/29 20:34
 * @Version 1.0.0
 */
public class ConditionTest {
	private static ConditionLock conditionLock = new ConditionLock();

    public static void main(String[] args) {
        for (int i=0; i<10; i++) {
            new PutThread("p"+i, i).start();
            new TakeThread("t"+i).start();
        }
    }

    static class PutThread extends Thread {
        private int num;
        public PutThread(String name, int num) {
            super(name);
            this.num = num;
        }
        public void run() {
            conditionLock.put(num);
        }
    }

    static class TakeThread extends Thread {
        public TakeThread(String name) {
            super(name);
        }
        public void run() {
                conditionLock.take();
        }
    }
}
