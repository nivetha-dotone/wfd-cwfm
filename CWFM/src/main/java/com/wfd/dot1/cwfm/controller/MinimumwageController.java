package com.wfd.dot1.cwfm.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wfd.dot1.cwfm.entity.CMSMinimumWage;
import com.wfd.dot1.cwfm.entity.CMSPrincipalEmployer;
import com.wfd.dot1.cwfm.entity.CMSWage;
import com.wfd.dot1.cwfm.service.CMSPRINCIPALEMPLOYERService;
import com.wfd.dot1.cwfm.service.MinimumWageService;

@Controller
@RequestMapping("/minimumwage")
public class MinimumwageController {
	 @Autowired
	    private MinimumWageService minimumWageService;

	    @Autowired
	    private CMSPRINCIPALEMPLOYERService principalEmployerService;

	    @GetMapping("/list")
	    public String getlist(Model model,
	                          @RequestParam(required = false) Long principalEmployerId,
	                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date effectiveDate) {
	        // Populate all principal employers
	        List<CMSPrincipalEmployer> principalEmployers = principalEmployerService.getAllCMSPRINCIPALEMPLOYERs();
	        model.addAttribute("principalEmployers", principalEmployers);

	        if (principalEmployerId != null) {
	            // If principalEmployerId is provided, set it as an attribute to maintain selection in the dropdown
	            model.addAttribute("selectedPrincipalEmployerId", principalEmployerId);

	            // Fetch minimum wages for the selected principal employer and effective date
	            List<CMSMinimumWage> minimumWages = minimumWageService.getMinimumWagesByPrincipalEmployerAndEffectiveDate(principalEmployerId, effectiveDate);
	            // Fetch wage details for each minimum wage
	            for (CMSMinimumWage minimumWage : minimumWages) {
	                CMSWage wage = minimumWageService.fetchCMSWageBasedOnWageId(minimumWage.getWageId());
	                minimumWage.setCmsWage(wage); // Set wage details in CMSMinimumWage object
	                
	            }
	            model.addAttribute("minimumWages", minimumWages);
	        }
	        
	        

	        return "minimumWage/list";
	    }

}
