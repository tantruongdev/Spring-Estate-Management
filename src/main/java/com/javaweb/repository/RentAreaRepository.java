package com.javaweb.repository;

import java.util.List;

public interface RentAreaRepository {
	List<Double> getListRentAreaById(Integer buildingId);
}
