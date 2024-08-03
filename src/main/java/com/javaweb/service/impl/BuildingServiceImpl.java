package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        List<BuildingEntity> buildingEntities = buildingRepository.findAll(params, typeCode);
        
        // Kiểm tra null và trả về danh sách trống nếu buildingEntities là null
        if (buildingEntities == null) {
            return new ArrayList<BuildingDTO>();
        }
        
        List<BuildingDTO> result = new ArrayList<BuildingDTO>();
        for (BuildingEntity entity : buildingEntities) {
            BuildingDTO dto = new BuildingDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setAddress(districtRepository.getNameById(entity.getDistrictId()) + ", " + entity.getWard() + ", " + entity.getStreet());
            dto.setNumberOfBasement(entity.getNumberOfBasement());
            dto.setManagerName(entity.getManagerName());
            dto.setManagerPhone(entity.getManagerPhone());
            dto.setFloorArea(entity.getFloorArea());
            dto.setRentPrice(entity.getRentPrice());
            dto.setServiceFee(entity.getServiceFee());
            dto.setBrokerageFee(entity.getBrokerageFee());
            
            // Lấy danh sách diện tích thuê và chuyển thành chuỗi
            List<Double> listRentArea = rentAreaRepository.getListRentAreaById(entity.getId());
            if (listRentArea != null) {
                StringBuilder rentAreaBuilder = new StringBuilder();
                for (int i = 0; i < listRentArea.size(); i++) {
                    if (i > 0) {
                        rentAreaBuilder.append(", ");
                    }
                    rentAreaBuilder.append(listRentArea.get(i).toString());
                }
                dto.setRentArea(rentAreaBuilder.toString());
            } else {
                dto.setRentArea(""); // hoặc một giá trị mặc định khác nếu listRentArea là null
            }
            
            result.add(dto);
        }
        return result;
    }
}
