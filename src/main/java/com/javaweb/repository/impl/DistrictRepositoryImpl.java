package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.repository.DistrictRepository;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository {
	static final String DB_URL = "jdbc:mysql://localhost:3307/estatebasic";
	static final String USERNAME = "root";
	static final String PASSWORD = "admin";
	@Override
	public String getNameById(Integer id) {
		// TODO Auto-generated method stub
		String sql = ("SELECT name FROM district WHERE id = " + id + ";");
		try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
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
