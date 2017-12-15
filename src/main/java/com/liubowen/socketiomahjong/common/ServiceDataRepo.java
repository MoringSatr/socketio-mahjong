package com.liubowen.socketiomahjong.common;

/**
 * @author liubowen
 * @date 2017/12/15 15:05
 * @description
 */
public interface ServiceDataRepo<T> {

    boolean save(T t);

    boolean update(T t);

    boolean delete(T t);
}
