package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.DepartmentMappingDao;
import com.wfd.dot1.cwfm.pojo.DeptMapping;

@Service
public class DepartmentMappingServiceImpl implements DepartmentMappingService {

	@Autowired
	DepartmentMappingDao deptMapDao;

	  @Override
	    public void saveMappings(List<DeptMapping> mappings) {
	        for (DeptMapping mapping : mappings) {
	            boolean exists = deptMapDao.existsMapping(mapping.getPrincipalEmployerId(), mapping.getDepartmentId());
	            if (exists) {
	            	 // fetch names from DAO
	                String principalEmployerName = deptMapDao.getPrincipalEmployerNameById(mapping.getPrincipalEmployerId());
	                String departmentName = deptMapDao.getDepartmentNameById(mapping.getDepartmentId());

	                throw new RuntimeException("Mapping already exists for Principal Employer: " 
	                        + principalEmployerName + " and Department: " + departmentName);
	             }
	        }
	        // If no duplicates, save all
	        deptMapDao.save(mappings);
	    }
	
	 @Override
	    public List<DeptMapping> getAllMappings() {
	        return deptMapDao.findAll();
	    }
}
