package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;

@Component
public class BuildingDTOConverter {
	  
    @Autowired
    private DistrictRepository districtRepository;
    
    @Autowired
    private RentAreaRepository rentAreaRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    
    // use Model Mapper
    public BuildingDTO toBuildingDTO(BuildingEntity buildingEntity) {
    	BuildingDTO buildingDTO = modelMapper.map(buildingEntity, BuildingDTO.class);
    	buildingDTO.setAddress(districtRepository.getNameById(buildingEntity.getDistrictId()) + ", " + buildingEntity.getWard() + ", " + buildingEntity.getStreet());
    	List<RentAreaEntity> listRentArea = rentAreaRepository.getValuByBuildingId(buildingEntity.getId());
    	String rentAreaResult = listRentArea.stream().map(it -> it.getValue().toString()).collect(Collectors.joining(", "));
    	buildingDTO.setRentArea(rentAreaResult);
    	return buildingDTO;
    }
    
    
//    public BuildingDTO toBuildingDTO(BuildingEntity buildingEntity) {
//    	 BuildingDTO buildingDTO = new BuildingDTO();
//         buildingDTO.setId(buildingEntity.getId());
//         buildingDTO.setName(buildingEntity.getName());
//         buildingDTO.setAddress(districtRepository.getNameById(buildingEntity.getDistrictId()) + ", " + buildingEntity.getWard() + ", " + buildingEntity.getStreet());
//         buildingDTO.setNumberOfBasement(buildingEntity.getNumberOfBasement());
//         buildingDTO.setManagerName(buildingEntity.getManagerName());
//         buildingDTO.setManagerPhone(buildingEntity.getManagerPhoneNumber());
//         buildingDTO.setFloorArea(buildingEntity.getFloorArea());
//         buildingDTO.setRentPrice(buildingEntity.getRentPrice());
//         buildingDTO.setServiceFee(buildingEntity.getServiceFee());
//         buildingDTO.setBrokerageFee(buildingEntity.getBrokerageFee());
//         List<RentAreaEntity> listRentArea = rentAreaRepository.getValuByBuildingId(buildingEntity.getId());
         // Dùng Stream
//         String rentAreaResult = listRentArea.stream().map(it -> it.getValue().toString()).collect(Collectors.joining(", "));
         // Lấy danh sách diện tích thuê và chuyển thành chuỗi
//       if (listRentArea.size() > 0) {
//           StringBuilder rentAreaBuilder = new StringBuilder();
//           for (int i = 0; i < listRentArea.size(); i++) {
//           	rentAreaBuilder.append(listRentArea.get(i).getValue().toString());
//               if (i > 0) {
//                   rentAreaBuilder.append(", ");
//               }
//               
//           }
//           dto.setRentArea(rentAreaBuilder.toString());
//       } else {
//           dto.setRentArea(""); // hoặc một giá trị mặc định khác nếu listRentArea là null
//       }
         
//         buildingDTO.setRentArea(rentAreaResult);
//         return buildingDTO;
//	}
    
    
}
