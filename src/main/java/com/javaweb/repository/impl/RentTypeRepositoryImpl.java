package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;

@Repository
public class RentTypeRepositoryImpl implements RentAreaRepository{
	static final String DB_URL = "jdbc:mysql://localhost:3307/estatebasic";
	static final String USERNAME = "root";
	static final String PASSWORD = "admin";
	@Override
	public List<Double> getListRentAreaById(Integer buildingId) {
		// TODO Auto-generated method stub
		String sql = ("SELECT value FROM rentarea WHERE buildingId = " + buildingId + ";");
		List<Double> result = new ArrayList<Double>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while(rs.next()) {
				result.add(rs.getDouble("value"));
			}
			return result;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
