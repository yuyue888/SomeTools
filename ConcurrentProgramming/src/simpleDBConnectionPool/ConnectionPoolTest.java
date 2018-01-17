package simpleDBConnectionPool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {
    private static ConnectionPool pool = new ConnectionPool(10);

    /**
     * 用于保证线程同时开始执行
     * CountDownLatch这个类能够使一个线程等待其他线程完成各自的工作后再执行
     */
    private static CountDownLatch start = new CountDownLatch(1);

    private static CountDownLatch end;

    static class ConnetionRunner implements Runnable {

        int count;

        AtomicInteger got;

        AtomicInteger notGot;

        public ConnetionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            try {
                start.await(); //等待start countDown再执行
            } catch (Exception ex) {
            }
            while (count > 0) {
                try {
                    //从线程池中获取连接，如果1000ms内无法获取到，将会返回null
                    //分别统计连接获取的数量got和未获取到的数量notGot
                    Connection connection = pool.fetchConnection(1000);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                    }
                } catch (Exception ex) {
                } finally {
                    count--;
                }
            }
            end.countDown();
        }
    }

    public static void main(String[] args) throws Exception {
        //线程数量，可以修改线程数量进行观察
        int threadCount = 10;
        end = new CountDownLatch(threadCount);
        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnetionRunner(count, got, notGot),
                    "ConnectionRunnerThread");
            thread.start();
        }
        start.countDown();//所有线程开始执行
        end.await();
        System.out.println("total invoke: " + (threadCount * count));
        System.out.println("got connection: " + got);
        System.out.println("not got connection " + notGot);
    }
}
