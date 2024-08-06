package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.DistrictRepository;
import com.javaweb.utils.ConnectionJDBCUtil;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository {

	@Override
	public String getNameById(Integer id) {
		// TODO Auto-generated method stub
		String sql = ("SELECT name FROM district WHERE id = " + id + ";");
		try (Connection conn = ConnectionJDBCUtil.getConnectionJdbc();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			if(rs.next()) {
				return rs.getString("name");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
