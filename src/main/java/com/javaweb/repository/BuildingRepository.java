package com.javaweb.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.BuildingDTO;


public interface BuildingRepository {
//      List<BuildingEntity> findAll(String name);
      List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode);
}
