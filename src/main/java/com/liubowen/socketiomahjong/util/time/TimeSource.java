package com.liubowen.socketiomahjong.util.time;

/**
 * @author liubowen
 * @date 2017/11/10 2:12
 * @description  时间源
 */
public abstract class TimeSource {

    public final long timeDisfference;

    public TimeSource(long timeDisfference) {
        this.timeDisfference = timeDisfference;
    }

    public abstract long currentTimeMillis();
}
