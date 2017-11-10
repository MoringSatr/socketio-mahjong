package com.liubowen.socketiomahjong.handle;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.session.Session;
import com.liubowen.socketiomahjong.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author liubowen
 * @date 2017/11/10 1:58
 * @description
 */
@Component
@Slf4j
public class LoginHandle {

    /**
     * 登陆
     */
    public void login(Session session, LoginVo loginVo) {
        if(session.isLogin()) {
            //已经登陆过的就忽略
            return;
        }
        //检查参数合法性
        if(!loginVo.isLegal()) {
            session.send("login_result", new ResultEntity(1, "invalid parameters").result());
            return;
        }
        //检查参数是否被篡改
        if(!loginVo.isMd5()) {
            session.send("login_result", new ResultEntity(2, "login failed. invalid sign!").result());
            return;
        }

    }

    public void logout(Session session) {

    }

    /** 断开链接 */
    public void disconnect(Session session) {
    }

    /** ping */
    public void gamePing(Session session, Object data) {
    }

}
