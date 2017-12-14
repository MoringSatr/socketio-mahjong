package com.liubowen.socketiomahjong.util.mybatis.typeHandler;

import com.google.common.collect.Lists;
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
@MappedJdbcTypes({ JdbcType.VARCHAR })
@MappedTypes({ List.class })
public class ListTypeHandler extends BaseTypeHandler<List> {

    public static final String MAJOR_DELIMITER = ",";// 默认的分隔符

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List parameter, JdbcType jdbcType) throws SQLException {
        StringBuilder builder = new StringBuilder();
        parameter.forEach(o -> {
            builder.append(o);
            builder.append(MAJOR_DELIMITER);
        });
        ps.setString(i, builder.substring(0, (builder.length() - 1)));
    }

    @Override
    public List getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String listsStr = rs.getString(columnName);
        return stringToList(listsStr);
    }

    @Override
    public List getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String listsStr = rs.getString(columnIndex);
        return stringToList(listsStr);
    }

    @Override
    public List getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String listsStr = cs.getString(columnIndex);
        return stringToList(listsStr);
    }

    private List stringToList(String value) {
        if (StringUtils.isBlank(value)) {
            return Lists.newArrayList();
        }
        List list = Lists.newArrayList();
        String[] listStrs = value.split(MAJOR_DELIMITER);
        for (String listStr : listStrs) {
            list.add((Object) listStr);
        }
        return list;
    }
}
