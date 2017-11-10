package com.liubowen.socketiomahjong.config;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author liubowen
 * @date 2017/11/9 21:21
 * @description
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    //TODO
    //FIXME 特别注意，该接口不能mybatis被扫描到，否则会出错
}
