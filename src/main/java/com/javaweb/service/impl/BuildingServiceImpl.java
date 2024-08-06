package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;
    
    @Autowired
    private BuildingDTOConverter buildingDTOConverter;
    
    @Override
    public List<BuildingDTO> findAll(Map<String, Object> params, List<String> typeCode) {
        List<BuildingEntity> buildingEntities = buildingRepository.findAll(params, typeCode);
        
        // Kiểm tra null và trả về danh sách trống nếu buildingEntities là null
        if (buildingEntities == null) {
            return new ArrayList<BuildingDTO>();
        }
        
        List<BuildingDTO> result = new ArrayList<BuildingDTO>();
        for (BuildingEntity entity : buildingEntities) {
            BuildingDTO buildingDTO = buildingDTOConverter.toBuildingDTO(entity);
            result.add(buildingDTO);
        }
        return result;
    }
}
