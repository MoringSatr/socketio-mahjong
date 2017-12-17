package com.liubowen.socketiomahjong.common;

/**
 * @author liubowen
 * @date 2017/12/15 15:05
 * @description
 */
public interface ServiceDataRepo<T> {

    void save(T t);

    void update(T t);

    void delete(T t);
}
