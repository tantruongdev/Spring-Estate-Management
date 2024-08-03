package com.javaweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.customException.FieldRequiredException;
import com.javaweb.model.BuildingDTO;
import com.javaweb.model.ErrorResponseDTO;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {

	@Autowired
	private BuildingService buildingService;
	
//	@GetMapping(value = "/api/buildings/")
//	public List<BuildingDTO> getBuildings(@RequestParam(name = "name") String name){
//		List<BuildingDTO> buildings = buildingService.findAll(name);
//		return buildings;
//	}
//	
	
//	@PostMapping(value = "/api/building/")
//	public Object getBuilding(@RequestBody BuildingDTO building) {
//		
//		try {
//			System.out.println(5/0);
//			validate(building);
//		}
//		catch (FieldRequiredException e) {
//			ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
//			errorResponseDTO.setError(e.getMessage());
//			List<String> details = new ArrayList<String>();
//			details.add("Kiem tra lai cac truong");
//			errorResponseDTO.setDetail(details);
//			return errorResponseDTO;
//		}
//		System.out.println(5/0);
		BuildingDTO building01 = new BuildingDTO();
//		building01.setName(nameBuilding);
//		building01.setNumberOfBasement(numberOfBasement);
//	
//		building01.setWard(ward);
//	
//		return building01;
//	}
	
//	public void validate( BuildingDTO buildingDTO) throws FieldRequiredException{
//		if (buildingDTO.getName() == null || buildingDTO.getName().equals("")
//		|| buildingDTO.getNumberOfBasement() == null) {
//			throw new FieldRequiredException("Name or number of basement is null");
//		}
//	}
	
	
//	@RequestMapping(value = "/api/building/")
//	public List<BuildingDTO> getBuilding(@RequestParam(value = "name", required = true) String nameBuilding,
//			@RequestParam() Integer numberOfBasement, @RequestParam(value = "ward", required = false) String ward) {
//		List<BuildingDTO> listBuildings = new ArrayList<>();
//		BuildingDTO building01 = new BuildingDTO();
//		BuildingDTO building02 = new BuildingDTO();
//		building01.setName(nameBuilding);
//		building02.setName("Tan Tower");
//		building01.setNumberOfBasement(numberOfBasement);
//		building02.setNumberOfBasement(5);
//		building01.setWard(ward);
//		building02.setWard("Di An");
//		listBuildings.add(building01);
//		listBuildings.add(building02);
//		return listBuildings;
//	}
	
//	@RequestMapping(value = "/api/building/")
//	public Object getBuilding(@RequestParam(value = "name", required = true) String nameBuilding,
//			@RequestParam() Integer numberOfBasement, @RequestParam(value = "ward", required = false) String ward) {
//		
//		try {
//			System.out.println(5/0);
//		}
//		catch (Exception e) {
//			ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
//			errorResponseDTO.setError(e.getMessage());
//			List<String> details = new ArrayList<String>();
//			details.add("So nguyen khong chia cho so 0 duoc");
//			errorResponseDTO.setDetail(details);
//			return errorResponseDTO;
//		}
//		BuildingDTO building01 = new BuildingDTO();
//		building01.setName(nameBuilding);
//		building01.setNumberOfBasement(numberOfBasement);
//	
//		building01.setWard(ward);
//	
//		return building01;
//	}
//	
	
}

