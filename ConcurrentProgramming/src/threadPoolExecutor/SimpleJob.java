package threadPoolExecutor;

import java.util.concurrent.TimeUnit;

public class SimpleJob implements Runnable {
    private int no;
    public SimpleJob() {
    }

    public SimpleJob(int no) {
        this.no = no;
    }

    @Override
    public void run() {
        try {
            System.out.println("--do job:"+this.no+"--");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
