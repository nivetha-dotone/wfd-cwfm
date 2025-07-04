package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ContractorService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/renewal")
public class ContractorRenewalController {

	@Autowired
	PrincipalEmployerService peService;

	@Autowired
	ContractorService contrService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	WorkmenService workmenService;


	@GetMapping("/list")
	public String getContractorRenewalList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		List<ContractorRegistration> listDto = contrService.getContractorRenewalList(String.valueOf(user.getUserId()));
		List<PrincipalEmployer> peList =new ArrayList<PrincipalEmployer>();
		 CMSRoleRights rr =new CMSRoleRights();
       if(user!=null) {
       if(user.getRoleName().equals("System Admin")) {
       	 rr.setAddRights(1);  // Changed getInt() to getBoolean()
		        rr.setEditRights(1);
		        rr.setDeleteRights(1);
		        rr.setImportRights(1);
		        rr.setExportRights(1);
		        rr.setViewRights(1);
       	peList = peService.getAllPrincipalEmployerForAdmin();
       }else {
       	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/renewal/list");
       	peList = peService.getAllPrincipalEmployer(user.getUserAccount());
       }
       }
		request.setAttribute("contractorlist", listDto);
		request.setAttribute("UserPermission", rr);
		return "contRenewal/list";

	}
	
	@GetMapping("/create")
	public String createRewnal(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		List<PrincipalEmployer> peList = workmenService.getAllPrincipalEmployer(user.getUserAccount());
		request.setAttribute("PrincipalEmployer", peList);
		
		String contractorRegId = contrService.generateContractorRegistrationId();
		request.setAttribute("contractorregId", contractorRegId);
		List<CmsGeneralMaster> gmList2 = workmenService.getAllGeneralMaster();

		// Grouping the CmsGeneralMaster objects by gmType
		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList2.stream()
		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

		// Define the types and their corresponding request attribute names
		Map<String, String> attributeMapping = Map.of(
		        "DOCUMENT TYPE", "DocumentType"
		        
		);

		// Iterate over the attribute mappings and set the request attributes dynamically
		attributeMapping.forEach((type, attributeName) -> {
		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
		    request.setAttribute(attributeName, gmList1);
		});
		
		
		return "contRenewal/create";
	}
	
	 @GetMapping("/getAllContractorDetailForRenewal")
	@ResponseBody 
		public ResponseEntity<ContractorRegistration> getAllContractorDetailForRenewal(
	            @RequestParam("unitId") String unitId, @RequestParam("contractorId") String contractorId, 
	            HttpServletRequest request,HttpServletResponse response) {
	        try {
	        	ContractorRegistration contractors=new ContractorRegistration();
	    			
	    				contractors = contrService.getAllContractorDetailForRenewal(unitId,contractorId);
	    			
	    				if (contractors == null || contractors.getContractorId() == null) {
	    		            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    		        }
	            return new ResponseEntity<>(contractors, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
}
