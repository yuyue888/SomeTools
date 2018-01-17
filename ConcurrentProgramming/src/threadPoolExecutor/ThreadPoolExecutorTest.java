package threadPoolExecutor;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolExecutorTest {

    public static void main(String[] args){
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        ExecutorService executor = Executors.newCachedThreadPool();
        ExecutorService executor = new ThreadPoolExecutor(2,3,1, TimeUnit.MINUTES,new ArrayBlockingQueue<Runnable>(2),new ThreadPoolExecutor.DiscardPolicy());
        List<SimpleJob> jobList = new LinkedList<>();
        jobList.add(new SimpleJob(1));
        jobList.add(new SimpleJob(2));
        jobList.add(new SimpleJob(3));
        jobList.add(new SimpleJob(4));
        jobList.add(new SimpleJob(5));
        jobList.add(new SimpleJob(6));

        for(SimpleJob job : jobList){
            executor.submit(job);
        }

    }
}
