package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	static final String DB_URL = "jdbc:mysql://localhost:3307/estatebasic";
	static final String USERNAME = "root";
	static final String PASSWORD = "admin";

	public static void joinQuery(Map<String, Object> params, List<String> typeCode, StringBuilder sql) {
		// Xác định bảng cần join
		// rentarea ra -> lấy ra value: rentArea : để kiểm tra areaFrom : areaTo
		// assignmentbuilding ab -> lấy ra staffId để kiểm tra nhân viên quản lí
		// buildingrenttype:-> lấy ra rentTypeId ->
		// renttype: -> lấy ra code
		if ((params.containsKey("areaFrom") && params.get("areaFrom").toString() != "")
				|| (params.containsKey("areaTo") && params.get("areaTo").toString() != "")) {
			sql.append(" INNER JOIN rentarea ra ON b.id = ra.buildingId ");
		}

		if (params.containsKey("staffId") && params.get("staffId").toString() != "") {
			sql.append(" INNER JOIN assignmentbuilding ab ON b.id = ab.buildingId ");
		}

		if (typeCode != null && typeCode.size() > 0) {
			sql.append(" INNER JOIN buildingrenttype brt ON brt.buildingId = b.id  ");
			sql.append(" INNER JOIN renttype rt ON rt.id = brt.rentTypeId ");
		}

	}

	// Thực hiện các lệnh WHERE cơ bản : = LIKE, so sánh trên chính Entity
	public static void normalQuery(Map<String, Object> params, StringBuilder sql) {
		// Xu li du lieu String
		if (params.get("name") != null && params.get("name") != "") {
			sql.append(" AND b.name LIKE '%" + params.get("name") + "%' ");
		}

		if (params.get("ward") != null && params.get("ward") != "") {
			sql.append(" AND b.ward LIKE '%" + params.get("ward") + "%' ");
		}

		if (params.get("street") != null && params.get("street") != "") {
			sql.append(" AND b.street LIKE '%" + params.get("street") + "%' ");
		}
		if (params.get("direction") != null && params.get("direction") != "") {
			sql.append(" AND b.direction LIKE '%" + params.get("direction") + "%' ");
		}
		if (params.get("level") != null && params.get("level") != "") {
			sql.append(" AND b.level LIKE '%" + params.get("level") + "%' ");
		}

		if (params.get("managerName") != null && params.get("managerName") != "") {
			sql.append(" AND b.managerName LIKE '%" + params.get("managerName") + "%' ");
		}

		if (StringUtil.isEmptyString(params.get("managerPhone").toString())) {
			sql.append(" AND b.managerPhone LIKE '%" + params.get("managerPhone") + "%' ");
		}

		// Xu li du lieu Number
		if (params.get("floorArea") != null && NumberUtil.isNumber(params.get("floorArea").toString())) {
			sql.append(" AND b.floorArea = " + Integer.parseInt(params.get("floorArea").toString()) + " ");
		}

		if (params.get("districtId") != null && NumberUtil.isNumber(params.get("districtId").toString())) {
			sql.append(" AND b.districtId = " + Integer.parseInt(params.get("districtId").toString()) + " ");
		}

		if (params.get("numberOfBasement") != null && NumberUtil.isNumber(params.get("numberOfBasement").toString())) {
			sql.append(
					" AND b.numberOfBasement = " + Integer.parseInt(params.get("numberOfBasement").toString()) + " ");
		}

	}

	// Thực hiện các lệnh WHERE phức tạp hơn: <= >= IN OR..., so sánh trên Entity,
	// các Entity khác
	public static void specialQuery(Map<String, Object> params, List<String> typeCode, StringBuilder sql) {
		// Xu li du lieu String

		// Xu li du lieu Number
		if (params.get("areaFrom") != null && NumberUtil.isNumber(params.get("areaFrom").toString())) {
			sql.append(" AND ra.value >= " + Double.parseDouble(params.get("areaFrom").toString()) + " ");
		}

		if (params.get("areaTo") != null && NumberUtil.isNumber(params.get("areaTo").toString())) {
			sql.append(" AND ra.value <= " + Double.parseDouble(params.get("areaTo").toString()) + " ");
		}
		if (params.get("rentPriceFrom") != null && NumberUtil.isNumber(params.get("rentPriceFrom").toString())) {
			sql.append(" AND b.rentPrice >= " + Double.parseDouble(params.get("rentPriceFrom").toString()) + " ");
		}

		if (params.get("rentPriceTo") != null && NumberUtil.isNumber(params.get("rentPriceTo").toString())) {
			sql.append(" AND b.rentPrice <= " + Double.parseDouble(params.get("rentPriceTo").toString()) + " ");
		}

		if (params.get("staffId") != null && NumberUtil.isNumber(params.get("staffId").toString())) {
			sql.append(" AND ab.staffId = " + Integer.parseInt(params.get("staffId").toString()) + " ");
		}

		if (typeCode != null && typeCode.size() > 0) {
			StringBuilder  typeSql = new StringBuilder();
			for (int i = 0; i < typeCode.size(); i++) {
				if (!typeCode.get(i).isEmpty()) {
					typeSql.append("'" + typeCode.get(i) + "', ");
				}
			}
			if (typeSql.length() > 0) {
				typeSql.delete(typeSql.length() - 2, typeSql.length());
				sql.append(" AND rt.code IN(" + typeSql + ") ");
				
			}
			
		}
		
	}

	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode) {
		// TODO Auto-generated method stub
		List<BuildingEntity> result = new ArrayList<BuildingEntity>();
		// Chuẩn bị query SQL
		// SELECT các trường của entity cần lấy
		StringBuilder sql = new StringBuilder(
				"SELECT b.id, b.name, b.ward, b.street, b.districtId, b.numberOfBasement, b.floorArea, b.rentPrice, b.serviceFee, b.brokerageFee, b.managerName, b.managerPhone FROM building b ");
		// JOIN -> tạo hàm xử lí join riêng : Đi tìm các bảng liên quan cần để lấy data
		joinQuery(params, typeCode, sql);
		// WHERE
		sql.append(" WHERE 1 = 1 ");
        normalQuery(params, sql);
        specialQuery(params, typeCode, sql);
        sql.append(" GROUP BY b.id;");
        try(Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        	Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql.toString());
        	){
        	while(rs.next()) {
        		BuildingEntity buildingEntity = new BuildingEntity();
        		buildingEntity.setId(rs.getInt("id"));
        		buildingEntity.setName(rs.getString("name"));
        		buildingEntity.setWard(rs.getString("ward"));
        		buildingEntity.setStreet(rs.getString("street"));
        		buildingEntity.setDistrictId(rs.getInt("districtId"));
        		buildingEntity.setNumberOfBasement(rs.getInt("numberOfBasement"));
//        		buildingEntity.setStructure(rs.getString("structure"));
        		buildingEntity.setFloorArea(rs.getInt("floorArea"));
//        		buildingEntity.setDirection(rs.getString("direction"));
//        		buildingEntity.setLevel(rs.getString("level"));
        		buildingEntity.setRentPrice(rs.getDouble("rentPrice"));
//        		buildingEntity.setRentPriceDescription(rs.getString("rentPriceDescription"));
        		buildingEntity.setServiceFee(rs.getDouble("serviceFee"));
        	
        		
        		buildingEntity.setBrokerageFee(rs.getDouble("brokerageFee"));
        		buildingEntity.setManagerName(rs.getString("managerName"));
        		buildingEntity.setManagerPhone(rs.getString("managerPhone"));
        		result.add(buildingEntity);
        	}
        	return result;
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        }
		return null;
	}
}