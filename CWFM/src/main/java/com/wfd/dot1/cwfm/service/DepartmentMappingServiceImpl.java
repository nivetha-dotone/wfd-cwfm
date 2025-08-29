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
	 
	 @Override
	    public List<DeptMapping> getAllTradeSkillMappings() {
	        return deptMapDao.findAllTradeSkillMappings();
	    }
	 @Override
	    public void saveTradeSkillMappings(List<DeptMapping> mappings) {
	        for (DeptMapping mapping : mappings) {
	            boolean exists = deptMapDao.existsTradeSkillMapping(mapping.getPrincipalEmployerId(), mapping.getTradeId(),mapping.getSkillId());
	            if (exists) {
	            	 // fetch names from DAO
	                String principalEmployerName = deptMapDao.getPrincipalEmployerNameById(mapping.getPrincipalEmployerId());
	                String tradeName = deptMapDao.getTradeNameById(mapping.getTradeId());
	                String skillName = deptMapDao.getTradeNameById(mapping.getSkillId());
	                
	                throw new RuntimeException("Mapping already exists for Principal Employer: " 
	                        + principalEmployerName + " , Trade: " + tradeName+ " and Skill: "+skillName);
	             }
	        }
	        // If no duplicates, save all
	        deptMapDao.saveTradeSkillMappings(mappings);
	    }
}
