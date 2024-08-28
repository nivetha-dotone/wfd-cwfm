package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.wfd.dot1.cwfm.entity.WorkmenWage;

public interface WorkmenWageService {
    int saveWorkmenWage(WorkmenWage workmenWage);
    List<WorkmenWage> getAllWorkmenWage();
    void updateWorkmenWage(WorkmenWage workmenWage);
    void updateWorkmenWages(List<WorkmenWage> workmenWages);
	void updateWorkmenWages(List<String> selectedIds, ModelMap model);
	  void updateWorkmenWages(List<String> selectedIds, List<WorkmenWage> updatedWorkmenWages);
	WorkmenWage findByWorkmenId(String workmenId);
}
