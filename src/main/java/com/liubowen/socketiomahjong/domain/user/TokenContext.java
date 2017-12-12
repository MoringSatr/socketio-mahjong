package com.liubowen.socketiomahjong.domain.user;

import com.google.common.collect.Maps;
import lombok.experimental.var;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentMap;

/**
 * @author liubowen
 * @date 2017/11/10 6:03
 * @description     用户token管理
 */
@Component
public class TokenContext {

    private ConcurrentMap<Long, UserToken> userTokenMap = Maps.newConcurrentMap();

    private ConcurrentMap<String, Long> token4userIdMap = Maps.newConcurrentMap();

    public UserToken get(long userId) {
        return this.userTokenMap.get(userId);
    }

    public UserToken getByToken(String token) {
        long userId = this.token4userIdMap.get(token);
        return this.userTokenMap.get(userId);
    }

    public UserToken remove(long userId) {
        UserToken userToken = this.userTokenMap.remove(userId);
        String token = userToken.getToken();
        this.token4userIdMap.remove(token);
        return userToken;
    }

    public void add(UserToken userToken) {
        this.userTokenMap.put(userToken.getUserId(), userToken);
        this.token4userIdMap.put(userToken.getToken(), userToken.getUserId());
    }

    public UserToken createToken(long userId, long lifeTime) {
        if(this.userTokenMap.containsKey(userId)) {
            this.remove(userId);
        }
        UserToken userToken = new UserToken(userId, lifeTime);
        this.add(userToken);
        return userToken;
    }

    public String getToken(long userId) {
        if(!this.userTokenMap.containsKey(userId)) {
            return null;
        }
        return this.get(userId).getToken();
    }

    public long getUserId(String token) {
        return this.token4userIdMap.get(token);
    }


    public boolean isTokenValid(String token) {
        UserToken userToken = this.getByToken(token);
        if(userToken == null) {
            return false;
        }
        return userToken.isTokenValid();
    }

    public void delToken(String token) {
        if(this.token4userIdMap.containsKey(token)) {
            long userId = this.token4userIdMap.get(token);
            this.remove(userId);
        }
    }

}
