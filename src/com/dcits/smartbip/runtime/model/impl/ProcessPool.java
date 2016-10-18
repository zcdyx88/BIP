package com.dcits.smartbip.runtime.model.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by vincentfxz on 16/5/26.
 */
public class ProcessPool {
    private static final int threadCount = 100;
    private ThreadPoolExecutor executor = null;

    private static ProcessPool processPool = new ProcessPool();

    public static ProcessPool getInstance() {
        if (null == processPool) {
            synchronized (ProcessPool.class) {
                if (null == processPool)
                    processPool = new ProcessPool();
            }
        }
        return processPool;
    }

    private ProcessPool() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(
                threadCount, new ThreadFactory() {
                    final ThreadGroup tg = new ThreadGroup("pool-process");
                    int count = 0;

                    public Thread newThread(Runnable r) {
                        return new Thread(tg, r, "pool-process-" + count++);
                    }

                });
    }

    public boolean dispatch(Runnable job) {
        executor.execute(job);
        return true;
    }

    public int getIdleThreads() {
        return executor.getPoolSize() - executor.getActiveCount();
    }

    public int getThreads() {
        return executor.getCorePoolSize();
    }

    public boolean isLowOnThreads() {
        return false;
    }

    public void join() throws InterruptedException {
        executor.shutdown();
    }

}
