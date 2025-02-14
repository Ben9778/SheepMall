package com.blackfiresoft.sheepmall.mq;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 抢购活动线程池管理
 */
public class RushThreadPool extends ThreadPoolExecutor implements AutoCloseable {

    private static final int CORE_THREAD_COUNT = 5;
    private static final int MAX_THREAD_COUNT = 10;
    private static final int KEEP_ALIVE_TIME = 60;

    public RushThreadPool() {
        super(CORE_THREAD_COUNT, MAX_THREAD_COUNT, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<>());
    }
}
