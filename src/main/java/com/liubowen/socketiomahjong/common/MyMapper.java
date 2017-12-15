package com.liubowen.socketiomahjong.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.condition.UpdateByConditionSelectiveMapper;

/**
 * @author liubowen
 * @date 2017/11/9 21:21
 * @description 特别注意，该接口不能mybatis被扫描到，否则会出错
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
