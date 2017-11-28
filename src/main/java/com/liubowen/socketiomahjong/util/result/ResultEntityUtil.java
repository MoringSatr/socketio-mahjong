package com.liubowen.socketiomahjong.util.result;

import com.liubowen.socketiomahjong.common.ResultEntity;

/**
 * @author liubowen
 * @date 2017/11/10 3:08
 * @description
 */
public class ResultEntityUtil {

    public static ResultEntity ok() {
        ResultEntity resultEntity = new ResultEntity(0, "ok");
        return resultEntity;
    }

    public static ResultEntity err() {
        return err("unknow error");
    }

    public static ResultEntity err(String errmsg) {
        ResultEntity resultEntity = new ResultEntity(1, errmsg);
        return resultEntity;
    }

    public static ResultEntity err(int errCode, String errmsg) {
        ResultEntity resultEntity = new ResultEntity(errCode, errmsg);
        return resultEntity;
    }
}
