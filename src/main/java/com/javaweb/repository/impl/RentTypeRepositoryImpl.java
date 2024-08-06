package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.entity.RentAreaEntity;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.utils.ConnectionJDBCUtil;

@Repository
public class RentTypeRepositoryImpl implements RentAreaRepository{

	@Override
	public List<RentAreaEntity> getValuByBuildingId(Integer buildingId) {
		// TODO Auto-generated method stub
		String sql = ("SELECT * FROM rentarea WHERE buildingId = " + buildingId + ";");
		List<RentAreaEntity> result = new ArrayList<RentAreaEntity>();
		try (Connection conn = ConnectionJDBCUtil.getConnectionJdbc();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while(rs.next()) {
				RentAreaEntity rentAreaEntity = new RentAreaEntity();
				rentAreaEntity.setId(rs.getInt("id"));
				rentAreaEntity.setBuildingId(rs.getInt("buildingId"));
				rentAreaEntity.setValue(rs.getInt("value"));
				result.add(rentAreaEntity);
				
			}
			return result;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
