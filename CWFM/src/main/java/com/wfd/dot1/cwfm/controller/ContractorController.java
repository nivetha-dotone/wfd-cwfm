package com.wfd.dot1.cwfm.controller;

import java.util.Arrays;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.service.ContractorService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkmenService;

@Controller
@RequestMapping("/contractor")
public class ContractorController {
	
	@Autowired
	WorkmenService workmenService;
	
	@Autowired
	PrincipalEmployerService peService;
	
	@Autowired
	ContractorService contrService;
	
	
	
	@GetMapping("/list")
    public String getAllPrincipalEmployer(HttpServletRequest request,HttpServletResponse response) {
    	 HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
         List<PrincipalEmployer> peList = workmenService.getAllPrincipalEmployer(user.getUserId());
 		request.setAttribute("principalEmployers", peList);
    	
    	 
    	return "contractors/list";
    }
	

	 
	 @PostMapping("/getAllContractorsBasedOnPE")
		public ResponseEntity<List<Contractor>> searchResources(
	            @RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,HttpServletRequest request,HttpServletResponse response) {
	        try {
	        	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
	            MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
	        
	        	  // Sample data (replace with your database queries)
		        List<Contractor> contractorList = workmenService.getAllContractorBasedOnPE(principalEmployerId, user.getUserId());
		        
		        

	            if (contractorList.isEmpty()) {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            }
	            return new ResponseEntity<>(contractorList, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	 }
	 
	 @GetMapping("/view/{contractorId}")
	    public String viewContractor(
	            @PathVariable("contractorId") String contractorId,
	            @RequestParam("principalEmployerId") String principalEmployerId,
	            HttpServletRequest request,HttpServletResponse response) {

	        // Use contractorId and principalEmployerId to fetch data as needed
	        Contractor contractor = contrService.getContractorById(contractorId);
	        request.setAttribute("contractor", contractor);
	        PrincipalEmployer principalEmployer = peService.getIndividualPEDetailByUnitId(principalEmployerId);
	        request.setAttribute("principalEmployer", principalEmployer);
	        
	        CMSContrPemm contractorPEMM = contrService.getMappingByContractorIdAndUnitId(contractorId, principalEmployerId);
	        request.setAttribute("contractorPEMM", contractorPEMM);
	        
	        List<CmsContractorWC> laborLicenses = contrService.getMappingsByContractorIdAndUnitIdAndLicenseType(contractorId, principalEmployerId, "LL");
	        request.setAttribute("laborLicenses", laborLicenses);
	        
	        List<String> licenseTypes = Arrays.asList("WC", "ESIC");
	        List<CmsContractorWC> contractorWCList = contrService.getMappingsByContractorIdAndUnitIdAndLicenseTypes(contractorId, principalEmployerId, licenseTypes);
	        request.setAttribute("contractorWCList", contractorWCList);
	        
	        List<Workorder> workOrderList = contrService.getWorkOrdersByContractorIdAndUnitId(contractorId, principalEmployerId);
	        request.setAttribute("workOrderList", workOrderList);

	        return "contractors/view";
	    }

}
