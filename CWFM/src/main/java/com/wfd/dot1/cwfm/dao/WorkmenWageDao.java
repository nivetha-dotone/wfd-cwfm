package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.entity.WorkmenWage;

public interface WorkmenWageDao {

	public int saveUser(WorkmenWage user);

	public List<WorkmenWage> getAllWorkmenWage();
	 public void updateWorkmenWages(List<WorkmenWage> workmenWages);
	 
	  public void updateWorkmenWage(WorkmenWage workmenWage);

	public WorkmenWage findById(String id);
	  WorkmenWage findByWorkmenId(String workmenId);
	    void saveOrUpdate(WorkmenWage workmenWage);
	   // List<WorkmenWage> findAll();
	 // void updateWorkmenWages(List<String> selectedIds, List<WorkmenWage> updatedWorkmenWages);
	
}
