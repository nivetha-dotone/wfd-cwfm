package com.wfd.dot1.cwfm.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.wfd.dot1.cwfm.dao.WorkmenWageDao;
import com.wfd.dot1.cwfm.entity.WorkmenWage;
@Service
public class WorkmenWageServiceImpl implements WorkmenWageService {
	 private static final Logger logger = LoggerFactory.getLogger(WorkmenWageService.class);

    @Autowired
    private WorkmenWageDao workmenWageDao;

    @Override
    @Transactional
    public int saveWorkmenWage(WorkmenWage workmenWage) {
        return workmenWageDao.saveUser(workmenWage);
    }

    @Override
    public List<WorkmenWage> getAllWorkmenWage() {
        return workmenWageDao.getAllWorkmenWage();
    }

    @Override
    @Transactional
    public void updateWorkmenWage(WorkmenWage workmenWage) {
    	 try {
    	        logger.info("Updating workmen wage: " + workmenWage);
    	        workmenWageDao.updateWorkmenWage(workmenWage);
    	        logger.info("Workmen wage updated successfully.");
    	    } catch (Exception e) {
    	        logger.error("Error updating workmen wage: " + e.getMessage());
    	        throw new RuntimeException("Failed to update workmen wage", e);
    	    }
    }
    @Override
    public void updateWorkmenWages(List<WorkmenWage> workmenWages) {
        try {
            for (WorkmenWage workmenWage : workmenWages) {
                workmenWageDao.updateWorkmenWage(workmenWage);
            }
        } catch (Exception e) {
            // Handle exceptions
            throw new RuntimeException("Failed to update workmen wages", e);
        }
    }
    @Override
    @Transactional
    public void updateWorkmenWages(List<String> selectedIds, List<WorkmenWage> updatedWorkmenWages) {
    	 logger.info("Workmen wage wwwwww" + selectedIds);
        for (int i = 0; i < selectedIds.size(); i++) {
        	
            String workmenId = selectedIds.get(i);
            logger.info("Workmen wage weeeeeeeee" + workmenId);
            WorkmenWage updatedWorkmenWage = updatedWorkmenWages.get(i);
            WorkmenWage existingWorkmenWage = workmenWageDao.findByWorkmenId(workmenId);
            logger.info("Workmen wage existingWorkmenWage" + existingWorkmenWage.getBASICPAY());
            logger.info("Workmen wage existingWorkmenWage" + ((WorkmenWage) updatedWorkmenWages).getBASICPAY());
            if (existingWorkmenWage != null) {
                existingWorkmenWage.setINSURANCE_TYPE(updatedWorkmenWage.getINSURANCE_TYPE());
                existingWorkmenWage.setWAGE_TYPE(updatedWorkmenWage.getWAGE_TYPE());
                existingWorkmenWage.setWKMEN_CATOGERY(updatedWorkmenWage.getWKMEN_CATOGERY());
                existingWorkmenWage.setBASICPAY(updatedWorkmenWage.getBASICPAY());
                existingWorkmenWage.setDA(updatedWorkmenWage.getDA());
                existingWorkmenWage.setHRA(updatedWorkmenWage.getHRA());
                existingWorkmenWage.setOther_Allowances(updatedWorkmenWage.getOther_Allowances());
                existingWorkmenWage.setUniform_Allowance(updatedWorkmenWage.getUniform_Allowance());
                existingWorkmenWage.setWashing_allowance(updatedWorkmenWage.getWashing_allowance());
                existingWorkmenWage.setStatutory_Bonus(updatedWorkmenWage.getStatutory_Bonus());
                existingWorkmenWage.setLeave_Encashment(updatedWorkmenWage.getLeave_Encashment());
                existingWorkmenWage.setEX_Serviceman_Allowance(updatedWorkmenWage.getEX_Serviceman_Allowance());
                existingWorkmenWage.setSupervisor_Allowance(updatedWorkmenWage.getSupervisor_Allowance());
                existingWorkmenWage.setHardship_Allowance(updatedWorkmenWage.getHardship_Allowance());
                existingWorkmenWage.setGunman_Allowance(updatedWorkmenWage.getGunman_Allowance());
                existingWorkmenWage.setTechnical_Allowance(updatedWorkmenWage.getTechnical_Allowance());
                existingWorkmenWage.setDriver_Allowance(updatedWorkmenWage.getDriver_Allowance());
                existingWorkmenWage.setQRT_Allowance(updatedWorkmenWage.getQRT_Allowance());
                existingWorkmenWage.setVALID_FROM(updatedWorkmenWage.getVALID_FROM());
                existingWorkmenWage.setVALID_TO(updatedWorkmenWage.getVALID_TO());
                existingWorkmenWage.setELIG_STATE(updatedWorkmenWage.getELIG_STATE());
                existingWorkmenWage.setACTIVE_STATUS(updatedWorkmenWage.getACTIVE_STATUS());
                existingWorkmenWage.setRECORD_UPDATED(updatedWorkmenWage.getRECORD_UPDATED());
                existingWorkmenWage.setPF_CAP(updatedWorkmenWage.getPF_CAP());
                existingWorkmenWage.setUNIQID(updatedWorkmenWage.getUNIQID());
                existingWorkmenWage.setBonusPayout(updatedWorkmenWage.getBonusPayout());
                existingWorkmenWage.setPF(updatedWorkmenWage.getPF());
                workmenWageDao.saveOrUpdate(existingWorkmenWage);
            }
        }
    }

    @Override
    public WorkmenWage findByWorkmenId(String workmenId) {
        return workmenWageDao.findByWorkmenId(workmenId);
    }
    public void updateWorkmenWages(List<String> selectedIds, ModelMap model) {
        try {
            // Retrieve WorkmenWage entities based on the selectedIds
            List<WorkmenWage> workmenWagesToUpdate = new ArrayList<>();
            for (String id : selectedIds) {
                 
                // Retrieve WorkmenWage entity based on id and add it to the list
                WorkmenWage workmenWage = workmenWageDao.findById(id); // Assuming you have a method to find by ID
                if (workmenWage != null) {
                    workmenWagesToUpdate.add(workmenWage);
                }
            }

            // Perform any necessary operations on the model if needed
            // For example, you might want to add attributes to the model:
            model.addAttribute("message", "Workmen wages updated successfully");

            // Update the retrieved WorkmenWage entities
            for (WorkmenWage workmenWage : workmenWagesToUpdate) {
                workmenWageDao.updateWorkmenWage(workmenWage);
            }
        } catch (Exception e) {
            // Handle exceptions
            throw new RuntimeException("Failed to update workmen wages", e);
        }
    }
//    @Transactional
//    public void updateWorkmenWage(String workmenId, BigDecimal newBasicPay, BigDecimal newDA) {
//        try {
//            logger.info("Updating workmen wage with ID: " + workmenId);
//            
//            // Retrieve the existing WorkmenWage object from the database
//            WorkmenWage existingWorkmenWage = workmenWageDao.findByWorkmenId(workmenId);
//            
//            if (existingWorkmenWage != null) {
//                // Update the specific fields
//                existingWorkmenWage.setBASICPAY(newBasicPay);
//                existingWorkmenWage.setDA(newDA);
//                
//                // Save the updated WorkmenWage object
//                workmenWageDao.updateWorkmenWage(existingWorkmenWage);
//                logger.info("Workmen wage updated successfully.");
//            } else {
//                logger.error("Workmen wage with ID " + workmenId + " not found.");
//                // Throw an exception or handle the case where the WorkmenWage entity is not found
//            }
//        } catch (Exception e) {
//            logger.error("Error updating workmen wage: " + e.getMessage());
//            throw new RuntimeException("Failed to update workmen wage", e);
//        }
//    }

}
