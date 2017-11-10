package com.liubowen.socketiomahjong.util.time;

/**
 * @author liubowen
 * @date 2017/11/10 2:13
 * @description 默认时间源（采用系统时间）
 */
public final class DefaultStandardTimeSource extends TimeSource {

    public DefaultStandardTimeSource(long timeDisfference) {
        super(timeDisfference);
    }

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
