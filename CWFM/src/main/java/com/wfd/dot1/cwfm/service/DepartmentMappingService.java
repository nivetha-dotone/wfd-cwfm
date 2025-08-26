package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.pojo.DeptMapping;

public interface DepartmentMappingService {

	void saveMappings(List<DeptMapping> mappings);
	
	List<DeptMapping> getAllMappings();
	

}
