package com.wfd.dot1.cwfm.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wfd.dot1.cwfm.entity.CMSPrincipalEmployer;
import com.wfd.dot1.cwfm.entity.WorkmenWage;
import com.wfd.dot1.cwfm.service.CMSPRINCIPALEMPLOYERService;
import com.wfd.dot1.cwfm.service.WorkmenWageService;

@Controller
@RequestMapping("/workmenwage")
public class WorkmenwageController {
    @Autowired
    private WorkmenWageService workmenWageService;
    
    private static final Logger logger = LoggerFactory.getLogger(WorkmenwageController.class);
    @Autowired
    private CMSPRINCIPALEMPLOYERService principalEmployerService;
    @RequestMapping("/workmenWageList")
    public String getWorkmenWageList(Model model) {
        List<WorkmenWage> workmenWageList = workmenWageService.getAllWorkmenWage();
        model.addAttribute("wwageList", workmenWageList);
        List<CMSPrincipalEmployer> principalEmployers = principalEmployerService.getAllCMSPRINCIPALEMPLOYERs();
        model.addAttribute("principalEmployers", principalEmployers);
        return "workmenWage/list";
    }
    @PostMapping("/update")
    public String updateWorkmenWages(@RequestBody Map<String, Object> requestData, RedirectAttributes redirectAttributes) {
        try {
        	 List<String> selectedIds = (List<String>) requestData.get("selectedIds");
             List<Map<String, String>> updatedData = (List<Map<String, String>>) requestData.get("updatedData");

             for (int i = 0; i < selectedIds.size(); i++) {
                 String workmenId = selectedIds.get(i);
                 WorkmenWage existingRecord = workmenWageService.findByWorkmenId(workmenId);
                 if (existingRecord != null) {
                     // Update all fields based on the provided data
                     Map<String, String> updatedRecord = updatedData.get(i);
                     existingRecord.setINSURANCE_TYPE(updatedRecord.get("INSURANCE_TYPE"));
                  //   existingRecord.setWAGE_TYPE(updatedRecord.get("WAGE_TYPE"));
                     existingRecord.setWKMEN_CATOGERY(updatedRecord.get("WKMEN_CATOGERY"));
                     existingRecord.setBASICPAY(updatedRecord.get("BASICPAY"));
                     existingRecord.setDA(updatedRecord.get("DA"));
                     existingRecord.setHRA(updatedRecord.get("HRA"));
                     existingRecord.setOther_Allowances(updatedRecord.get("Other_Allowances"));
                     existingRecord.setUniform_Allowance(updatedRecord.get("Uniform_Allowance"));
                     existingRecord.setWashing_allowance(updatedRecord.get("Washing_allowance"));
                     existingRecord.setStatutory_Bonus(updatedRecord.get("Statutory_Bonus"));
                     existingRecord.setLeave_Encashment(updatedRecord.get("Leave_Encashment"));
                     existingRecord.setEX_Serviceman_Allowance(updatedRecord.get("EX_Serviceman_Allowance"));
                     existingRecord.setSupervisor_Allowance(updatedRecord.get("Supervisor_Allowance"));
                     existingRecord.setHardship_Allowance(updatedRecord.get("Hardship_Allowance"));
                     existingRecord.setGunman_Allowance(updatedRecord.get("Gunman_Allowance"));
                     existingRecord.setTechnical_Allowance(updatedRecord.get("Technical_Allowance"));
                     existingRecord.setDriver_Allowance(updatedRecord.get("Driver_Allowance"));
                     existingRecord.setQRT_Allowance(updatedRecord.get("QRT_Allowance"));
                     existingRecord.setVALID_FROM(updatedRecord.get("VALID_FROM"));
                     existingRecord.setVALID_TO(updatedRecord.get("VALID_TO"));
                     existingRecord.setELIG_STATE(updatedRecord.get("ELIG_STATE"));
                     existingRecord.setACTIVE_STATUS(updatedRecord.get("ACTIVE_STATUS"));
                     existingRecord.setRECORD_UPDATED(updatedRecord.get("RECORD_UPDATED"));
                     existingRecord.setPF_CAP(updatedRecord.get("PF_CAP"));
                     existingRecord.setUNIQID(updatedRecord.get("UNIQID"));
                     existingRecord.setBonusPayout(updatedRecord.get("BonusPayout"));
                     existingRecord.setPF(updatedRecord.get("PF"));

                     workmenWageService.updateWorkmenWage(existingRecord);
                 } else {
                     // Handle the case where the record with the given ID is not found
                	 redirectAttributes.addFlashAttribute("recordNotFound", true);
                 }
             }
            redirectAttributes.addFlashAttribute("updateSuccess", true);
        } catch (Exception e) {
            e.printStackTrace();
            // Add a flash attribute to indicate a failed update
            redirectAttributes.addFlashAttribute("updateFailed", true);
        }
        
        // Redirect to the list view
        return "redirect:/workmenwage/workmenWageList";
    }


    private WorkmenWage convertToWorkmenWage(Map<String, Object> workmenWageData) {
        // Implement conversion logic from Map to WorkmenWage object
        // For example:
        WorkmenWage workmenWage = new WorkmenWage();
        workmenWage.setBASICPAY((String) workmenWageData.get("BASICPAY"));
        workmenWage.setDA((String)workmenWageData.get("DA"));
        workmenWage.setHRA( (String)workmenWageData.get("HRA"));
        return workmenWage;
    }
    
    
}
