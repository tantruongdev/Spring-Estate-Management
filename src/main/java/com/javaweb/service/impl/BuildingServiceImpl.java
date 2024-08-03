package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private RentAreaRepository rentAreaRepository;
	
	@Override
	public List<BuildingDTO> findAll(Map<String, Object> params, List<String> typeCode) {
		// TODO Auto-generated method stub
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(params, typeCode);
		// Kiểm tra null và trả về danh sách trống nếu buildingEntities là null
	    if (buildingEntities == null) {
	        return new ArrayList<>();
	    }
		List<BuildingDTO> result = new ArrayList<BuildingDTO>();
		for (BuildingEntity entity : buildingEntities) {
			BuildingDTO dto = new BuildingDTO();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setAddress(districtRepository.getNameById(entity.getDistrictId()) + ", "  + entity.getWard() + ", " + entity.getStreet());
			dto.setNumberOfBasement(entity.getNumberOfBasement());
			dto.setManagerName(entity.getManagerName());
			dto.setManagerPhone(entity.getManagerPhone());
			dto.setFloorArea(entity.getFloorArea());
			dto.setRentPrice(entity.getRentPrice());
			dto.setServiceFee(entity.getServiceFee());
			dto.setBrokerageFee(entity.getBrokerageFee());
			List<Double> listRentArea = rentAreaRepository.getListRentAreaById(entity.getId());
			List<String> rentAreaStrings = listRentArea.stream()
			                                         .map(String::valueOf)
			                                         .collect(Collectors.toList());
			String joinedRentArea = String.join(", ", rentAreaStrings);
			dto.setRentArea(joinedRentArea);
			result.add(dto);
		}
		return result;
//		return null;

	}

}

