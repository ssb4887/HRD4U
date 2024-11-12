package rbs.modules.report.mapper;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import oracle.sql.CLOB;

public class NCLOBTypeHandler extends BaseTypeHandler<String>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
			throws SQLException {
		CLOB clob = CLOB.createTemporary(ps.getConnection(), false, CLOB.DURATION_SESSION);
		clob.setString(1, parameter);
		ps.setClob(i, clob);
	}

	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		CLOB clob = (CLOB) rs.getClob(columnName);
		return clob == null ? null : clob.getSubString(1, (int) clob.length());
	}

	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
