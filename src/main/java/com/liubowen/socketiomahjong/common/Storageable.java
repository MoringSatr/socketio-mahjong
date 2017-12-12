package com.liubowen.socketiomahjong.common;

/**
 * @author liubowen
 * @date 2017/12/12 11:02
 * @description 存储接口，继承该接口的类必须是spring管理的。 每过一段时间会调用storage()方法
 */
public interface Storageable {

    void storage();
}
