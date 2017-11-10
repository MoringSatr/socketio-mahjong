package com.liubowen.socketiomahjong.util.time;

import java.util.concurrent.TimeUnit;

/**
 * @author liubowen
 * @date 2017/11/10 2:14
 * @description 虚拟时间源，当改变时间之后切换为该时间源，该时间源拥有自己的计算时间线程
 */
public class HackedTimeSource extends TimeSource {

    private volatile long currentTimeMillis;
    private volatile long lastExecTime;

    public HackedTimeSource(long timeDisfference) {
        this(timeDisfference, System.currentTimeMillis());
    }

    public HackedTimeSource(long timeDisfference, long currentTimeMillis) {
        super(timeDisfference);
        this.currentTimeMillis = currentTimeMillis;
        this.lastExecTime = System.currentTimeMillis();
        start();
    }

    private void start() {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    long now = System.currentTimeMillis();
                    currentTimeMillis += (now - lastExecTime);
                    lastExecTime = now;
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        //ignore
                    }
                }
            }
        };
        t.setDaemon(true);
        t.start();
    }

    @Override
    public long currentTimeMillis() {
        return currentTimeMillis;
    }
}
