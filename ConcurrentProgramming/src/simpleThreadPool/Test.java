package simpleThreadPool;

import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        DefaultThreadPool defaultThreadPool = new DefaultThreadPool(10);

        for(int i=0;i<20;i++){
            defaultThreadPool.execute(new SimpleJob(i));
        }
    }

    static public class SimpleJob implements Runnable {
        private int no ;
        public SimpleJob(int no) {
            this.no = no;
        }

        @Override
        public void run() {
            System.out.println(this.no +"当前时间:"+System.currentTimeMillis());
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
