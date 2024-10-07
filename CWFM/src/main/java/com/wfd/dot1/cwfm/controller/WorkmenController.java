package com.wfd.dot1.cwfm.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dto.ApproveRejectGatePassDto;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.enums.UserRole;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Skill;
import com.wfd.dot1.cwfm.pojo.Trade;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.service.WorkmenService;

@Controller
@RequestMapping("/contractworkmen")
public class WorkmenController {

	private static final Logger log = LoggerFactory.getLogger(WorkmenController.class.getName());
	
	@Autowired
	WorkmenService workmenService;
	
	@GetMapping("/addQuickOB")
    public String createGatePass(@RequestParam("userId") String userId,HttpServletRequest request,HttpServletResponse response) {
		log.info("Entered into addQuickOBForm"+userId);
		//Principal Employer List
		List<PrincipalEmployer> peList = workmenService.getAllPrincipalEmployer(userId);
		request.setAttribute("PrincipalEmployer", peList);
		
		//Department And Area/Sub department List
		List<CmsGeneralMaster> list = workmenService.getAllDepartmentAndSubDepartment(userId);
		Map<String, List<CmsGeneralMaster>> groupedByAuthorizationOn = list.stream()
                .collect(Collectors.groupingBy(CmsGeneralMaster::getAuthorizationOn));
        List<CmsGeneralMaster> departments = groupedByAuthorizationOn.getOrDefault("department", new ArrayList<>());
        List<CmsGeneralMaster> subDepartments = groupedByAuthorizationOn.getOrDefault("subdepartment", new ArrayList<>());
        request.setAttribute("Dept", departments);
        request.setAttribute("Subdept", subDepartments);
        
       
       
        //Skills
		List<Skill> skillList = workmenService.getAllSkill();
		request.setAttribute("Skills", skillList);
		
		//Eic 
		List<MasterUser> eicList = workmenService.getAllEicManager(userId);
		request.setAttribute("EIC", eicList);
		
		//Get All GeneralMaster
		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

		// Grouping the CmsGeneralMaster objects by gmType
		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

		// Define the types and their corresponding request attribute names
		Map<String, String> attributeMapping = Map.of(
		        "GENDER", "GenderOptions",
		        "BLOODGROUP", "BloodGroup",
		        "ACCESSAREA", "AccessArea",
		        "ACADEMICS", "Academics",
		        "WAGECATEGORY", "WageCategory",
		        "BONUSPAYOUT", "BonusPayout",
		        "ZONE", "Zone"
		);

		// Iterate over the attribute mappings and set the request attributes dynamically
		attributeMapping.forEach((type, attributeName) -> {
		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
		    request.setAttribute(attributeName, gmList1);
		});

		
		
        return "contractWorkmen/quickOBAdd";
    }
	
	
    
    @GetMapping("/getAllContractors")
	public ResponseEntity<List<Contractor>> getAllContractors(
            @RequestParam("unitId") String unitId, 
            @RequestParam("userId") String userId) {
        log.info("Fetching contractors for unitId: " + unitId + " and userId: " + userId);
        try {
            List<Contractor> contractors = workmenService.getAllContractorBasedOnPE(unitId, userId);
            if (contractors.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(contractors, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching contractors: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getAllWorkOrders")
    public ResponseEntity<List<Workorder>> getAllWorkorders(
    		@RequestParam("unitId") String unitId, 
            @RequestParam("contractorId") String contractorId){
    	 log.info("Entering into getAllWorkorders for: " + unitId + " and contractorId: " + contractorId);
    	 try {
    		 List<Workorder> workorders = workmenService.getAllWorkordersBasedOnPEAndContractor(unitId,contractorId);
    		 if(workorders.isEmpty()) {
    			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		 }
    		 return new ResponseEntity<>(workorders,HttpStatus.OK);
    	 }catch(Exception e) {
    		 log.error("Error fetching workorders: ", e);
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	 }
    
    	
    }
    
    @GetMapping("/getAllWC")
    public ResponseEntity<List<CmsContractorWC>> getAllWCs(
    		@RequestParam("unitId") String unitId, 
            @RequestParam("contractorId") String contractorId){
    	 log.info("Entering into getAllWCs for: " + unitId + " and contractorId: " + contractorId);
    	 try {
    		 List<CmsContractorWC> wcs = workmenService.getAllWCBasedOnPEAndCont(unitId,contractorId);
    		 if(wcs.isEmpty()) {
    			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		 }
    		 return new ResponseEntity<>(wcs,HttpStatus.OK);
    	 }catch(Exception e) {
    		 log.error("Error fetching getAllWCs: ", e);
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	 }
    
    	
    }
    
    @GetMapping("/getAllTrades")
    public ResponseEntity<List<Trade>> getAllTrades(@RequestParam("unitId")String unitId){
    	log.info("Entered into getAllTrades for unitId:"+unitId);
    	try {
    		List<Trade> trades = workmenService.getAllTradesBasedOnPE(unitId);
    		if(trades.isEmpty()) {
    			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		}
    		return new ResponseEntity<>(trades,HttpStatus.OK);
    	}catch(Exception e) {
    		log.error("Error fetching trades: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    
    }
    
    private static final String ROOT_DIRECTORY = "D:/wfd_cwfm/ep_docs/";

    @PostMapping("/uploadDocuments")
    public ModelAndView uploadDocuments(@RequestParam("aadharFile") MultipartFile aadharFile,
                                        @RequestParam("policeFile") MultipartFile policeFile) {
        // Replace this with actual userId or any unique identifier
        String userId = "1234";  // You can get this dynamically

        // Create directory path
        String directoryPath = ROOT_DIRECTORY + userId + "/";
        
        try {
            // Ensure the directory exists, if not create it
            Path path = Paths.get(directoryPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // Save Aadhar PDF
            if (!aadharFile.isEmpty()) {
                String aadharFilePath = directoryPath + "aadhar.pdf";
                saveFile(aadharFile, aadharFilePath);
            }

            // Save Police Verification PDF
            if (!policeFile.isEmpty()) {
                String policeFilePath = directoryPath + "police.pdf";
                saveFile(policeFile, policeFilePath);
            }

            // Return success message
            return new ModelAndView("/contractWorkmen/quickOBList");

        } catch (IOException e) {
            e.printStackTrace();
            // Return error message
            return new ModelAndView("/contractWorkmen/quickOBList", "message", "Error uploading documents: " + e.getMessage());
        }
    }

    // Utility method to save file
    private void saveFile(MultipartFile file, String path) throws IOException {
        byte[] bytes = file.getBytes();
        Path filePath = Paths.get(path);
        Files.write(filePath, bytes);
    }
    
    @PostMapping("/saveGatePass")
    public ResponseEntity<String> saveGatePass(@RequestBody GatePassMain gatePassMain,HttpServletRequest request,HttpServletResponse response) {
    	String gatePassId=null;
    	// Log the GatePassMain object as JSON
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String gatePassMainJson = objectMapper.writeValueAsString(gatePassMain);
            log.info("Received GatePassMain JSON: {}", gatePassMainJson);
        } catch (Exception e) {
            log.error("Error converting GatePassMain to JSON", e);
        }
        try {
        	
        	gatePassMain.setCreatedBy(gatePassMain.getUserId());
        	gatePassId = workmenService.saveGatePass(gatePassMain);
        	if(null!=gatePassId) {
        		return new ResponseEntity<>("contractWorkmen/list",HttpStatus.OK);
        	}
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error saving data: " + e.getMessage());
        } 
    }
    
    @GetMapping("/quickOBList")
    public String gatePassListingDetails(HttpServletRequest request,HttpServletResponse response) {
    	 HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	List<GatePassListingDto> listDto = new ArrayList<GatePassListingDto>();
    	if(null!=user) {
    		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			listDto= workmenService.getGatePassListingDetails(user.getUserId());
    		}else {
    			listDto = workmenService.getGatePassListingForApprovers(user);
    		}
    		request.setAttribute("contractorWorkmen", listDto);
    	}
    	 
    	return "contractWorkmen/quickOBList";
    }
    
    @GetMapping("/view/{gatePassId}")
    public String viewIndividualContractWorkmenDetails(@PathVariable String gatePassId,HttpServletRequest request,HttpServletResponse response) {
    	log.info("Entered into viewIndividualContractWorkmenDetails: "+gatePassId);
    	GatePassMain gatePassMainObj =null;
    	try {
    		gatePassMainObj = workmenService.getIndividualContractWorkmenDetails(gatePassId);
    		request.setAttribute("GatePassObj", gatePassMainObj);
          
    		//Get All GeneralMaster
    		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMasterForGatePass(gatePassMainObj);
    		for (CmsGeneralMaster generalMaster : gmList) {
    		    String gmType = generalMaster.getGmType();
    		if ("GENDER".equals(gmType)) {
    	        gatePassMainObj.setGender(generalMaster.getGmName()); 
    	    } else if ("BLOODGROUP".equals(gmType)) {
    	        gatePassMainObj.setBloodGroup(generalMaster.getGmName()); 
    	    } else if ("ACADEMIC".equals(gmType)) {
    	        gatePassMainObj.setAcademic(generalMaster.getGmName()); 
    	    } else if ("ZONE".equals(gmType)) {
    	        gatePassMainObj.setZone(generalMaster.getGmName()); 
    	    } else if ("ACCESSAREA".equals(gmType)) {
    	        gatePassMainObj.setAccessArea(generalMaster.getGmName()); 
    	    } else if ("WAGECATEGORY".equals(gmType)) {
    	        gatePassMainObj.setWageCategory(generalMaster.getGmName()); 
    	    } else if ("BONUSPOUT".equals(gmType)) {
    	        gatePassMainObj.setBonusPayout(generalMaster.getGmName()); 
    	    } else if("DEPARTMENT".equals(gmType)){
    	    	gatePassMainObj.setDepartment(generalMaster.getGmName());
    	    } else if("AREA".equals(gmType)) {
    	    	gatePassMainObj.setSubdepartment(generalMaster.getGmName());
    	    }
    		}
    		
    		 
    	}catch(Exception e) {
    		log.error("Error getting workmen details ", e);
    	}
    	log.info("Exiting from viewIndividualContractWorkmenDetails: "+gatePassId);
    	return "contractWorkmen/view";
    }
    @PostMapping("/approveRejectGatePass")
    public ResponseEntity<String> approveRejectGatePass(@RequestBody ApproveRejectGatePassDto dto,HttpServletRequest request,HttpServletResponse response) {
    	String result=null; 
    	try {
             ObjectMapper objectMapper = new ObjectMapper();
             String approveRejectGatePass = objectMapper.writeValueAsString(dto);
             log.info("Received approveRejectGatePass JSON: {}", approveRejectGatePass);
         } catch (Exception e) {
             log.error("Error converting approveRejectGatePass to JSON", e);
         }
         try {
        	 result = workmenService.approveRejectGatePass(dto);
         	if(null!=result) {
         		return new ResponseEntity<>("contractWorkmen/list",HttpStatus.OK);
         	}
         	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                  .body("Error saving data: " + e.getMessage());
         } 
    }
    
}
