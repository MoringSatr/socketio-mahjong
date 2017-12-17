package com.liubowen.socketiomahjong.util.mybatis.typeHandler;

import com.google.common.collect.Lists;
import com.liubowen.socketiomahjong.util.conver.ListConver;
import com.liubowen.socketiomahjong.util.conver.MapConver;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liubowen
 * @date 2017/12/14 15:22
 * @description
 */
@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({Map.class})
public class IntegerListMapTypeHandler extends BaseTypeHandler<Map<Integer, List<Integer>>> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Map<Integer, List<Integer>> integerListMap, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, MapConver.integerListMapConverToString(integerListMap));
    }

    @Override
    public Map<Integer, List<Integer>> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String value = resultSet.getString(s);
        return MapConver.StringConverToIntegerListMap(value);
    }

    @Override
    public Map<Integer, List<Integer>> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String value = resultSet.getString(i);
        return MapConver.StringConverToIntegerListMap(value);
    }

    @Override
    public Map<Integer, List<Integer>> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String value = callableStatement.getString(i);
        return MapConver.StringConverToIntegerListMap(value);
    }
}
