package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
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

import com.wfd.dot1.cwfm.dto.WorkorderResponseDto;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ContractorService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkmenService;
import com.wfd.dot1.cwfm.service.WorkorderService;

@Controller
@RequestMapping("/workorders")
public class WorkorderController {
	
	@Autowired
	PrincipalEmployerService peService;
	
	@Autowired
	ContractorService contrService;
	
	@Autowired
	WorkmenService workmenService;
	
	@Autowired
	WorkorderService woService;
	
	@Autowired
	CommonService commonService;
	 @GetMapping("/list")
	    public String getlist(@RequestParam(required = false) String principalEmployerId,@RequestParam(required = false)  String contractorId,HttpServletRequest request,HttpServletResponse response) {
		 HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		 //List<PrincipalEmployer> peList = workmenService.getAllPrincipalEmployer(user.getUserAccount());
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
	 	       	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/workorders/list");
	 	       	peList = peService.getAllPrincipalEmployer(user.getUserAccount());
	 	       }
	 	       }
	        request.setAttribute("principalEmployers", peList);
	        request.setAttribute("UserPermission", rr);
	        if (principalEmployerId != null) {
	            request.setAttribute("selectedPrincipalEmployerId", principalEmployerId);
	            
	           // List<Contractor> contList =  workmenService.getAllContractorBasedOnPE(principalEmployerId,user.getUserAccount());
	            
	            List<Contractor> contList = new ArrayList<Contractor>();
				if(user!=null) {
				if("System Admin".equals(user.getRoleName())) {
					contList = workmenService.getAllContractorForAdmin(principalEmployerId);
				}else {
					contList = workmenService.getAllContractorBasedOnPE(principalEmployerId,
							user.getUserAccount());
				}
				
				}
				request.setAttribute("contractors", contList);
	            request.setAttribute("selectedContractorId", contractorId);
	            
	            
	            List<Workorder> woList = contrService.getWorkOrdersByContractorIdAndUnitId(principalEmployerId,contractorId);
	            request.setAttribute("woList", woList);
	            
	            PrincipalEmployer principalEmployer = peService.getIndividualPEDetailByUnitId(principalEmployerId);
	            request.setAttribute("principalEmployer", principalEmployer);
	            
	            Contractor contractor = contrService.getContractorById(contractorId);
		        request.setAttribute("contractor", contractor);
	        }

	        return "workorder/list";
	    }
	    
	 @PostMapping("/getAllWorkordersBasedOnPEAndContractor")
		public ResponseEntity<WorkorderResponseDto> getAllWorkordersBasedOnPEAndContractor(
	            @RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
	            @RequestParam(value = "contractorId", required = false) String contractorId,
	            HttpServletRequest request,HttpServletResponse response) {
	        try {
	        	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
	            MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
	        
	            List<Workorder> woList = contrService.getWorkOrdersByContractorIdAndUnitId(principalEmployerId,contractorId);
	            
	            PrincipalEmployer principalEmployer = peService.getIndividualPEDetailByUnitId(principalEmployerId);
	            
	            Contractor contractor = contrService.getContractorById(contractorId);
		        
		        WorkorderResponseDto responseDto = new WorkorderResponseDto();
		        responseDto.setWoList(woList);
		        responseDto.setPrincipalEmployer(principalEmployer);
		        responseDto.setContractor(contractor);
		        
	            if (woList.isEmpty()) {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            }
	            return new ResponseEntity<>(responseDto, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	 }
	 
	 @GetMapping("/view/{id}")
	    public String viewContractors(@PathVariable("id") String id,@RequestParam(required = false) String principalEmployerId,@RequestParam(required = false) String contractorId, HttpServletRequest request,HttpServletResponse response) {
	    	Workorder workorder =woService.getWorkorderById(id);
	       request.setAttribute("workorder", workorder);
	       
	        PrincipalEmployer principalEmployer =peService.getIndividualPEDetailByUnitId(principalEmployerId);
	       request.setAttribute("principalEmployer", principalEmployer);
	       
	        Contractor contractor =contrService.getContractorById(contractorId);
	       request.setAttribute("contractor", contractor);
	        return "workorder/view";
	    } 

}
