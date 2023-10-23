package com.colonb.naafa.mybatis;

import com.colonb.naafa.user.enums.HospitalRole;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(HospitalRole.class)
public class
HospitalRoleHandler extends BaseTypeHandler<HospitalRole> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, HospitalRole parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public HospitalRole getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : HospitalRole.valueOf(value);
    }

    @Override
    public HospitalRole getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : HospitalRole.valueOf(value);
    }

    @Override
    public HospitalRole getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : HospitalRole.valueOf(value);
    }
}