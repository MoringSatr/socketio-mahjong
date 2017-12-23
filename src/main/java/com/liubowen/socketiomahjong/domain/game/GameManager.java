package com.liubowen.socketiomahjong.domain.game;

import net.sf.json.JSONObject;

/**
 * @author liubowen
 * @date 2017/11/10 15:23
 * @description
 */
public abstract class GameManager {

    public abstract void setReady(long userId);

    public abstract void chuPai(long userId, String pai);

    public abstract void dingQue(long userId, String que);

    public abstract void huanSanZhang(long userId, String p1, String p2, String p3);

    public abstract void peng(long userId);

    public abstract void gang(long userId, String pai);

    public abstract void hu(long userId);

    public abstract void guo(long userId);

    public abstract boolean hasBegan();

    public abstract JSONObject dissolveRequest(long userId);

    public abstract JSONObject dissolveAgree(long userId, boolean b);

    public abstract void doDissolve();
}
