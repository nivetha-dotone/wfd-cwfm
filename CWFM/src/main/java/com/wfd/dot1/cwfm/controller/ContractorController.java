package com.wfd.dot1.cwfm.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wfd.dot1.cwfm.entity.CMSContrPemm;
import com.wfd.dot1.cwfm.entity.CMSContractor;
import com.wfd.dot1.cwfm.entity.CMSContractorWC;
import com.wfd.dot1.cwfm.entity.CMSPrincipalEmployer;
import com.wfd.dot1.cwfm.entity.CMSWorkOrder;
import com.wfd.dot1.cwfm.entity.State;
import com.wfd.dot1.cwfm.service.CMSPRINCIPALEMPLOYERService;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ContrPemmService;
import com.wfd.dot1.cwfm.service.ContractorService;
@Controller
@RequestMapping("/contractor")
public class ContractorController {
	 @Autowired
	    private ContractorService contractorService;
	  @Autowired
	    private CMSPRINCIPALEMPLOYERService principalEmployerService;
	  @Autowired
	  private ContrPemmService pemmService;
	  @Autowired
	  private CommonService commonService;
	  @GetMapping("/list")
	    public String getlist(Model model,
	                          @RequestParam(required = false) Long principalEmployerId) {
	        // Populate all principal employers
	        List<CMSPrincipalEmployer> principalEmployers = principalEmployerService.getAllCMSPRINCIPALEMPLOYERs();
	        model.addAttribute("principalEmployers", principalEmployers);
	        System.err.println("Error occurred while handling file filePath: " + principalEmployerId);
	        if (principalEmployerId != null) {
	            // If principalEmployerId is provided, set it as an attribute to maintain selection in the dropdown
	            model.addAttribute("selectedPrincipalEmployerId", principalEmployerId);
	            // Fetch minimum wages for the selected principal employer and effective date
	            List<CMSContractor> contList = contractorService.getContractorsByPrincipalEmployerId(principalEmployerId);
	            // Fetch wage details for each minimum wage
	            System.err.println("Error occurred while handling file filePath: " + contList.get(0).getCode());
	            model.addAttribute("contList", contList);
	        }

	        return "contractors/list";
	    }
	    
	    
	    @GetMapping("/view/{id}")
	    public String viewContractors(@PathVariable Long id,@RequestParam(required = false) Long principalEmployerId,Model model) {
	    	CMSContractor contractor = contractorService.getContractorById(id);
	        model.addAttribute("contractor", contractor);
	        CMSPrincipalEmployer principalEmployer = principalEmployerService.getCMSPRINCIPALEMPLOYERById(principalEmployerId);
	        model.addAttribute("principalEmployer", principalEmployer);
	        CMSContrPemm contractorPEMM = pemmService.getMappingByContractorIdAndUnitId(id, principalEmployerId);
	        model.addAttribute("contractorPEMM", contractorPEMM);
	        List<CMSContractorWC> laborLicenses = commonService.getMappingsByContractorIdAndUnitIdAndLicenseType(id, principalEmployerId, "LL");
	        model.addAttribute("laborLicenses", laborLicenses);
	        
	        List<String> licenseTypes = Arrays.asList("WC", "ESIC");
	        List<CMSContractorWC> contractorWCList = commonService.getMappingsByContractorIdAndUnitIdAndLicenseTypes(id, principalEmployerId, licenseTypes);
	        model.addAttribute("contractorWCList", contractorWCList);
	        
	        List<CMSWorkOrder> workOrderList = commonService.getWorkOrdersByContractorIdAndUnitId(id, principalEmployerId);
	        model.addAttribute("workOrderList", workOrderList);

	        
	        return "contractors/view";
	    }

	    @GetMapping("/add")
	    public String addPrincipalEmployerForm(Model model) {
	        model.addAttribute("contractor", new CMSContractor());
	        return "contractor/add";
	    }

	    @RequestMapping(path = "/add", method = RequestMethod.POST)
	    public String addPrincipalEmployer(HttpServletRequest request, @ModelAttribute("contractor") CMSContractor contractor, 
	            BindingResult result, Model model) {
	         int selectedStateId = Integer.parseInt(request.getParameter("selectedState"));
	      //  PrincipalEmployerValidator.validate(contractor, result);
	        
//	        int errorsLength = result.getErrorCount(); // Assuming errors is a BindingResult object
//	        model.addAttribute("errorsLength", errorsLength);
	        if (result.hasErrors()) {
	        	model.addAttribute("errors", result);
	            return "contractors/add"; // Return to the form page if validation fails
	        }
	       
	       
	       
	        // Now you can save the principalEmployer object to the database
	        contractorService.addContractor(contractor);

	        return "redirect:/contractors/list";
	    }
	    @GetMapping("/search")
	    public String searchPrincipalEmployers(@RequestParam("query") String query, Model model) {
	        List<CMSContractor> searchResults = contractorService.searchContractor(query);
	        model.addAttribute("contractor", searchResults);
	        return "contractors/list";
	    }
	    
	    @GetMapping("/edit/{id}")
	    public String editPrincipalEmployerForm(@PathVariable Long id, Model model) {
	    	CMSContractor contractor = contractorService.getContractorById(id);
	        model.addAttribute("contractor", contractor);
	        return "contractors/edit";
	    }
	    
	    
	    @RequestMapping(path = "/edit/{id}", method = RequestMethod.POST)
	    public String editPrincipalEmployer(HttpServletRequest request, @PathVariable String id,
				@ModelAttribute("contractor") CMSPrincipalEmployer contractor, BindingResult result, Model model) {
	     
	    	int selectedStateId = Integer.parseInt(request.getParameter("selectedState"));
	    	//PrincipalEmployerValidator.validate(contractor, result);
	        if (result.hasErrors()) {
	        	model.addAttribute("errors", result);
	            return "contractors/edit"; // Return to the form page if validation fails
	        }
	       // contractorService.updateContractor(contractor);

	        // Redirect to the view page
	        return "redirect:/contractors/list";
	    }
	    
	    @GetMapping("/contReg")
	    public String addContReg(Model model) {
	        model.addAttribute("contractor", new CMSContractor());
	        return "contractors/addContReg";
	    }
	    @RequestMapping(path = "registration", method = RequestMethod.POST)
	    public String addPrincipalEmployer(HttpServletRequest request, @ModelAttribute("contReg") CMSContractor contractor, 
	             @RequestParam("file") MultipartFile file, BindingResult result, Model model) {
	        
//	        int errorsLength = result.getErrorCount(); // Assuming errors is a BindingResult object
//	        model.addAttribute("errorsLength", errorsLength);
	       
	        // Handle file upload
	       
	        // Now you can save the principalEmployer object to the database
//	        cmSPRINCIPALEMPLOYERService.addCMSPRINCIPALEMPLOYER(principalEmployer);
	        return "redirect:/contractors/list";
	    }
}
