package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRentTypeRepository;
import com.javaweb.utils.ConnectionJDBCUtil;

@Repository
public class BuildingRentTypeRepositoryImpl implements BuildingRentTypeRepository{

	
	@Override
	public Integer getRenTypeIdByBuildingId(Integer id) {
		// TODO Auto-generated method stub
		String sql = "SELECT renTypeId FROM buildingrenttype WHERE buildingId = "  + id + ";";
		try(Connection conn = ConnectionJDBCUtil.getConnectionJdbc();
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
