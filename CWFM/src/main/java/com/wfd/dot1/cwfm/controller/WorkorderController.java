package com.wfd.dot1.cwfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.entity.CMSContractor;
import com.wfd.dot1.cwfm.entity.CMSPrincipalEmployer;
import com.wfd.dot1.cwfm.entity.CMSWorkOrder;
import com.wfd.dot1.cwfm.service.CMSPRINCIPALEMPLOYERService;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ContractorService;

@Controller
@RequestMapping("/workorders")
public class WorkorderController {
	 @Autowired
	    private CMSPRINCIPALEMPLOYERService principalEmployerService;
	  @Autowired
	  private CommonService commonService;
	  @Autowired
	    private ContractorService contractorService;
	  @GetMapping("/list")
	    public String getlist(Model model,
	                          @RequestParam(required = false) Long principalEmployerId,@RequestParam(required = false)  Long contractorId) {
	        // Populate all principal employers
	        List<CMSPrincipalEmployer> principalEmployers = principalEmployerService.getAllCMSPRINCIPALEMPLOYERs();
	        model.addAttribute("principalEmployers", principalEmployers);
//	        List<CMSContractor> contallList = contractorService.getAllContractors();
//            model.addAttribute("contractors", contallList);
	        System.err.println("Error occurred while handling file filePath: " + principalEmployerId);
	        if (principalEmployerId != null) {
	            // If principalEmployerId is provided, set it as an attribute to maintain selection in the dropdown
	            model.addAttribute("selectedPrincipalEmployerId", principalEmployerId);
	            // Fetch minimum wages for the selected principal employer and effective date 
	            List<CMSContractor> contList = contractorService.getContractorsByPrincipalEmployerId(principalEmployerId);
	            // Fetch wage details for each minimum wage
	            System.err.println("Error occurred while handling file filePath: " + contList.get(0).getCode());
	            model.addAttribute("contractors", contList);
	            model.addAttribute("selectedContractorId", contractorId);
	            List<CMSWorkOrder> woList = commonService.getWorkOrdersByContractorIdAndUnitId(principalEmployerId,contractorId);
	            // Fetch wage details for each minimum wage
	            System.err.println("Error occurred while handling file filePath: " + woList);
	            model.addAttribute("woList", woList);
	            
	            CMSPrincipalEmployer principalEmployer = principalEmployerService.getCMSPRINCIPALEMPLOYERById(principalEmployerId);
	            model.addAttribute("principalEmployer", principalEmployer);
	            CMSContractor contractor = contractorService.getContractorById(contractorId);
		        model.addAttribute("contractor", contractor);
	        }

	        return "workorder/list";
	    }
	    
	    
	    @GetMapping("/view/{id}")
	    public String viewContractors(@PathVariable Long id,@RequestParam(required = false) Long principalEmployerId,@RequestParam(required = false) Long contractorId,Model model) {
	    	CMSWorkOrder workorder = commonService.getWorkorderById(id);
	        model.addAttribute("workorder", workorder);
	        CMSPrincipalEmployer principalEmployer = principalEmployerService.getCMSPRINCIPALEMPLOYERById(principalEmployerId);
	        model.addAttribute("principalEmployer", principalEmployer);
	        CMSContractor contractor = contractorService.getContractorById(contractorId);
	        model.addAttribute("contractor", contractor);
	        return "workorder/view";
	    }
	    
	    @GetMapping("/contractors")
	    @ResponseBody
	    public List<CMSContractor> getContractorsByPrincipalEmployerId(@RequestParam Long principalEmployerId) {
	        return contractorService.getContractorsByPrincipalEmployerId(principalEmployerId);
	    }
}
