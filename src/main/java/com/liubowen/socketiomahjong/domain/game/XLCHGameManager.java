package com.liubowen.socketiomahjong.domain.game;

import net.sf.json.JSONObject;

/**
 * @author liubowen
 * @date 2017/11/10 19:06
 * @description
 */
public class XLCHGameManager extends GameManager {
    @Override
    public void doDissolve() {

    }

    @Override
    public void setReady(long userId) {

    }

    @Override
    public void chuPai(long userId, String pai) {

    }

    @Override
    public void dingQue(long userId, String que) {

    }

    @Override
    public void huanSanZhang(long userId, String p1, String p2, String p3) {

    }

    @Override
    public void peng(long userId) {

    }

    @Override
    public void gang(long userId, String pai) {

    }

    @Override
    public void hu(long userId) {

    }

    @Override
    public void guo(long userId) {

    }

    @Override
    public boolean hasBegan() {
        return false;
    }

    @Override
    public JSONObject dissolveRequest(long userId) {
        return null;
    }

    @Override
    public JSONObject dissolveAgree(long userId, boolean b) {
        return null;
    }
}
