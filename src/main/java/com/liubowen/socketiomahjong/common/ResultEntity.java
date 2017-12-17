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

    /** 错误码 */
    private int errcode;

    /** 错误信息 */
    private String errmsg;

    /** 返回信息 */
    private Map<String, Object> params;

    /** 是否发送错误码和错误信息 */
    private boolean sendErr;

    public ResultEntity(int errcode, String errmsg, Map<String, Object> params) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.params = params;
        this.sendErr = true;
    }

    public ResultEntity() {
        this.params = Maps.newHashMap();
        this.sendErr = false;
    }

    public ResultEntity(Map<String, Object> params) {
        this.params = params;
        this.sendErr = false;
    }

    public ResultEntity(int errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.params = Maps.newHashMap();
        this.sendErr = true;
    }

    public void add(String key, Object value) {
        this.params.put(key, value);
    }

    public void addAll(Map<String, Object> params) {
        this.params.putAll(params);
    }

    /** 获取发送返回消息 */
    public Map<String, Object> result() {
        if (sendErr) {
            this.params.put("errcode", errcode);
            this.params.put("errmsg", errmsg);
        }
        return this.params;
    }

    public boolean ok() {
        return this.errcode == 0;
    }

    public Object get(String key) {
        return this.params.get(key);
    }

    public boolean containsKey(String key) {
        return this.params.containsKey(key);
    }

}
