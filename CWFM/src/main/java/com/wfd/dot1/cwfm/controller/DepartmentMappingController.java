package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.enums.DotType;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.DeptMapping;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.WorkmenBulkUpload;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.DepartmentMappingService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/departmentMapping")
public class DepartmentMappingController {

	@Autowired
	DepartmentMappingService deptMapService;
	
	@Autowired
	PrincipalEmployerService peService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	WorkmenService workmenService;
	
	@GetMapping("/list")
	public String getAllModules(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		
		
		/*List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
    	//List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
    	//List<PersonOrgLevel> subdepartments = groupedByLevelDef.getOrDefault("Area", new ArrayList<>());
    	request.setAttribute("PrincipalEmployer", peList);
    	 // request.setAttribute("Dept", departments);
         // request.setAttribute("Subdept", subdepartments);
          
    	List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
        CMSRoleRights rr =new CMSRoleRights();
        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/departmentMapping/list");
   	    listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
   	    request.setAttribute("UserPermission", rr);*/
		
		List<PrincipalEmployer> listDto = peService.getAllPrincipalEmployerForAdmin();
		request.setAttribute("PrincipalEmployer", listDto);
    	
   	 List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

		// Grouping the CmsGeneralMaster objects by gmType
		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

		// Define the types and their corresponding request attribute names
		Map<String, String> attributeMapping = Map.of(
		        "AREA", "AreaOptions",
		        "DEPARTMENT", "Department"
		        
		);

		// Iterate over the attribute mappings and set the request attributes dynamically
		attributeMapping.forEach((type, attributeName) -> {
		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
		    request.setAttribute(attributeName, gmList1);
		});

				
		return "deptMapping/unitDeptMapping";
	}
	@PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> saveMappings(@RequestBody List<DeptMapping> mappings) {
        try {
        	deptMapService.saveMappings(mappings);
            return ResponseEntity.ok("All mappings saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving mappings: " + e.getMessage());
        }
    }

	
	 @GetMapping("/existinglist")
	    public String getAllMappings(HttpServletRequest request,HttpServletResponse response,Model model) {
	    	 HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
	         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
	         List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
	         CMSRoleRights rr =new CMSRoleRights();
	         if(user!=null) {
	         if(user.getRoleName().equals("System Admin")) {
	        	 rr.setAddRights(1);  // Changed getInt() to getBoolean()
			        rr.setEditRights(1);
			        rr.setDeleteRights(1);
			        rr.setImportRights(1);
			        rr.setExportRights(1);
			        rr.setViewRights(1);
	        	 listDto = peService.getAllPrincipalEmployerForAdmin();
	         }else {
	        	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/workmenBulkUpload/list");
	        	 //listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
	         }
	         }
	    		//request.setAttribute("cmSPRINCIPALEMPLOYERs", listDto);
	    		request.setAttribute("UserPermission", rr);
	    		
	    		List<DeptMapping> wbu = deptMapService.getAllMappings();
	    	   	 model.addAttribute("wbudata", wbu);
	    	return "deptMapping/view";
	    }
}
