package com.liubowen.socketiomahjong.util.mybatis.typeHandler;

import com.google.common.collect.Lists;
import com.liubowen.socketiomahjong.util.Punctuation;
import com.liubowen.socketiomahjong.util.conver.ListConver;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author liubowen
 * @date 2017/12/14 15:22
 * @description 该ListTypeHandler 只能适用于简单的数据类型，long,int,String[中不许'，'],boolean,byte
 */
@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({List.class})
public class ListTypeHandler extends BaseTypeHandler<List> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List parameter, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, ListConver.listConverToString(parameter));
    }

    @Override
    public List getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String listsStr = resultSet.getString(columnName);
        return ListConver.stringConverToList(listsStr);
    }

    @Override
    public List getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        String listsStr = resultSet.getString(columnIndex);
        return ListConver.stringConverToList(listsStr);
    }

    @Override
    public List getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        String listsStr = callableStatement.getString(columnIndex);
        return ListConver.stringConverToList(listsStr);
    }

}
