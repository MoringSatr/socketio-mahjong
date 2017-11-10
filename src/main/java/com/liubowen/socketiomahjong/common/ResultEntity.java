package com.liubowen.socketiomahjong.common;

import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author liubowen
 * @date 2017/11/10 2:58
 * @description json数据返回
 */
@Setter
@ToString
public class ResultEntity {

    private int errcode;

    private String errmsg;

    private Map<String, Object> params;

    public ResultEntity(int errcode, String errmsg, Map<String, Object> params) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.params = params;
    }

    public ResultEntity(int errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.params = params = Maps.newHashMap();
    }

    public void add(String key, Object value) {
        this.params.put(key, value);
    }

    public void add(Map<String, Object> params) {
        this.params.putAll(params);
    }

    /** 获取发送返回消息（包含errcode,errmsg） */
    public Map<String, Object> result() {
        this.params.put("errcode", errcode);
        this.params.put("errmsg", errmsg);
        return this.params;
    }

    /** 获取发送返回消息（不包含errcode,errmsg） */
    public Map<String, Object> resultNoErr() {
        return this.params;
    }

}
