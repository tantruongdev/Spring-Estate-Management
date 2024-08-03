package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRentTypeRepository;

@Repository
public class BuildingRentTypeRepositoryImpl implements BuildingRentTypeRepository{
	static final String DB_URL = "jdbc:mysql://localhost:3307/estatebasic";
	static final String USERNAME = "root";
	static final String PASSWORD = "admin";
	
	@Override
	public Integer getRenTypeIdByBuildingId(Integer id) {
		// TODO Auto-generated method stub
		String sql = "SELECT renTypeId FROM buildingrenttype WHERE buildingId = "  + id + ";";
		try(Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
				){
			return rs.getInt("rentTypeId");
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
}
