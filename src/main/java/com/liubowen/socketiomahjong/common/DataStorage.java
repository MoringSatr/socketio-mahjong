package com.liubowen.socketiomahjong.common;

/**
 * @author liubowen
 * @date 2017/12/15 15:08
 * @description
 */
public class DataStorage {

    private volatile int op = 0;

    public void onOption(Option op) {
        this.op = (this.op | op.code);
    }

    public boolean isInsert() {
        return Option.INSERT.match(op);
    }

    public boolean isDelete() {
        return Option.DELETE.match(op);
    }

    public boolean updatable() {
        return this.op != 0;
    }

    public int update() {
        int ret = this.op;
        this.op = 0;
        return ret;
    }

    public void commit(int op) {
        // DO NOTHING
    }

    public void cancel(int op, int cause) {
        if ((Option.INSERT.match(op))/* && cause == Updatable.DUPLICATE_KEY_ERROR */) {
            // 已有相同记录, 去掉insert标记
            op = (op & (0xffff ^ Option.INSERT.code));
        }
        this.op = op;
    }

    public enum Option {
        NONE(0), INSERT(1), UPDATE(2), DELETE(4);
        public final int code;

        Option(int code) {
            this.code = code;
        }

        public boolean match(int op) {
            return (this.code & op) != 0;
        }
    }

}
