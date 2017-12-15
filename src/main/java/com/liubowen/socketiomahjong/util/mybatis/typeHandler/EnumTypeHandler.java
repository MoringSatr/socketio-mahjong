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
 * @description
 */
@MappedJdbcTypes({ JdbcType.VARCHAR })
@MappedTypes({ List.class })
public class EnumTypeHandler extends BaseTypeHandler<Enum> {

    public static final String MAJOR_DELIMITER = ",";// 默认的分隔符

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Enum anEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, anEnum.name() + MAJOR_DELIMITER + anEnum.ordinal());
    }

    @Override
    public Enum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String enumName = resultSet.getString(s);
        return Enum.valueOf(Enum.class, enumName);
    }

    @Override
    public Enum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String enumName = resultSet.getString(i);
        return Enum.valueOf(Enum.class, enumName);
    }

    @Override
    public Enum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String enumName = callableStatement.getString(i);
        return Enum.valueOf(Enum.class, enumName);
    }

}
