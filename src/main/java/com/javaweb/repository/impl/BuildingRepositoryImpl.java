package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.utils.ConnectionJDBCUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

	public static void joinQuery(Map<String, Object> params, List<String> typeCode, StringBuilder sql) {
		// Xác định bảng cần join
//		String areaFrom = (String)params.get("areaFrom");
//		String areaTo = (String)params.get("areaTo");
//		if (StringUtil.checkString(areaFrom)
//				|| StringUtil.checkString(areaTo)) {
//			sql.append(" INNER JOIN rentarea ra ON b.id = ra.buildingId ");
//		}

		if (StringUtil.checkString(params.get("staffId").toString())) {
			sql.append(" INNER JOIN assignmentbuilding ab ON b.id = ab.buildingId ");
		}

		if (typeCode != null && !typeCode.isEmpty()) {
			boolean joinAdded = false;
			for (String value : typeCode) {
				if (StringUtil.checkString(value)) {
					if (!joinAdded) {
						sql.append(" INNER JOIN buildingrenttype brt ON brt.buildingId = b.id ");
						sql.append(" INNER JOIN renttype rt ON rt.id = brt.rentTypeId ");
						joinAdded = true;
					}
				}
			}
		}
	}

	private static void addLikeCondition(StringBuilder where, String key, String value) {
		where.append(" AND b." + key + " LIKE '%" + value + "%' ");
	}

	private static void addEqualCondition(StringBuilder where, String key, String value) {
		where.append(" AND b." + key + " = " + value + " ");

	}

	// Thực hiện các lệnh WHERE cơ bản : = LIKE, so sánh trên chính Entity
	public static void normalQuery(Map<String, Object> params, StringBuilder where) {
		for (Map.Entry<String, Object> it : params.entrySet()) {
			if (!it.getKey().equals("staffId") && !it.getKey().equals("typeCode") && !it.getKey().startsWith("area")
					&& !it.getKey().startsWith("rentPrice")) {
				String key = (String)it.getKey();
				String value = (String)it.getValue();
				if (StringUtil.checkString(value)) {
					if (NumberUtil.isNumber(value)) {
						// Xử lý dữ liệu Number
						addEqualCondition(where, key, value);
					} else {
						// Xử lý dữ liệu String
						addLikeCondition(where, key, value);
					}
				}
			}
		}

	}

	// Thực hiện các lệnh WHERE phức tạp hơn: <= >= IN OR..., so sánh trên Entity,
	// các Entity khác
	public static void specialQuery(Map<String, Object> params, List<String> typeCode, StringBuilder where) {
	
		String staffId = params.get("staffId").toString();
		if (StringUtil.checkString(staffId)) {
			where.append(" AND ab.staffId = " + staffId + " ");
		}

		String areaFrom = (String)params.get("areaFrom");
		String areaTo = (String)params.get("areaTo");
		// use EXISTS
		where.append(" AND EXISTS (SELECT * FROM rentarea ra WHERE b.id = ra.buildingid ");
		if (StringUtil.checkString(areaFrom)) {
			where.append(" AND ra.value >= " + areaFrom + " ");
		}
		if (StringUtil.checkString(areaTo)) {
			where.append(" AND ra.value <= " + areaTo + " ");
		}
		where.append(") ");
		
		String rentPriceFrom = (String)params.get("rentPriceFrom");
		String rentPriceTo = (String)params.get("rentPriceTo");
		if (StringUtil.checkString(rentPriceFrom)) {
			where.append(" AND b.rentPrice >= " + rentPriceFrom + " ");
		}
		if (StringUtil.checkString(rentPriceTo)) {
			where.append(" AND b.rentPrice <= " + rentPriceTo + " ");
		}

		// handle typeCode
		// use Java 8
		if (typeCode != null && !typeCode.isEmpty()) {
			where.append(" AND (");
			String sql = typeCode.stream()
								.map(it -> "rt.code LIKE " + "'%" + it + "%'")
								.collect(Collectors.joining(" OR "));
			where.append(sql);
			where.append(" ) ");
		}
		
//		if (typeCode != null && !typeCode.isEmpty()) {
//			StringBuilder typeSql = new StringBuilder();
//			for (String code : typeCode) {
//				if (code != null && !code.trim().isEmpty()) {
//					if (typeSql.length() > 0) {
//						typeSql.append(", ");
//					}
//					typeSql.append("'").append(code).append("'");
//				}
//			}
//			if (typeSql.length() > 0) {
//				where.append(" AND rt.code IN (").append(typeSql).append(") ");
//			}
//		}
	}

	

	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode) {
		// TODO Auto-generated method stub
		List<BuildingEntity> result = new ArrayList<BuildingEntity>();
		// Chuẩn bị query SQL
		// SELECT các trường của entity cần lấy
		StringBuilder sql = new StringBuilder(
				"SELECT b.id, b.name, b.ward, b.street, b.districtId, b.numberOfBasement, b.floorArea, b.rentPrice, b.serviceFee, b.brokerageFee, b.managerName, b.managerPhoneNumber FROM building b ");
		// JOIN -> tạo hàm xử lí join riêng : Đi tìm các bảng liên quan cần để lấy data
		joinQuery(params, typeCode, sql);
		// WHERE
		StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
		
		normalQuery(params, where);
		specialQuery(params, typeCode, where);
		sql.append(where);
		sql.append(" GROUP BY b.id;");
		try (Connection conn = ConnectionJDBCUtil.getConnectionJdbc();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {
			while (rs.next()) {
				BuildingEntity buildingEntity = new BuildingEntity();
				buildingEntity.setId(rs.getInt("id"));
				buildingEntity.setName(rs.getString("name"));
				buildingEntity.setWard(rs.getString("ward"));
				buildingEntity.setStreet(rs.getString("street"));
				buildingEntity.setDistrictId(rs.getInt("districtId"));
				buildingEntity.setNumberOfBasement(rs.getInt("numberOfBasement"));
				buildingEntity.setFloorArea(rs.getInt("floorArea"));
				buildingEntity.setRentPrice(rs.getDouble("rentPrice"));
				buildingEntity.setServiceFee(rs.getDouble("serviceFee"));
				buildingEntity.setBrokerageFee(rs.getDouble("brokerageFee"));
				buildingEntity.setManagerName(rs.getString("managerName"));
				buildingEntity.setManagerPhoneNumber(rs.getString("managerPhoneNumber"));
				result.add(buildingEntity);
			}
			return result;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}