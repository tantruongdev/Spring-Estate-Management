package com.javaweb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingController {

	@Autowired
	private BuildingService buildingService;

	@GetMapping("/api/buildings/")
	public List<BuildingDTO> getBuildings(@RequestParam Map<String, Object> params,
										  @RequestParam(value="typeCode", required=false) List<String> typeCode
				) {

		List<BuildingDTO> result = buildingService.findAll(params, typeCode);
		return result;
	}

}
