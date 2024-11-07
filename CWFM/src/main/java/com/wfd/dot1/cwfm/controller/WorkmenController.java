package com.wfd.dot1.cwfm.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dto.ApproveRejectGatePassDto;
import com.wfd.dot1.cwfm.dto.GatePassActionDto;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.enums.GatePassType;
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

    public String uploadDocuments( MultipartFile aadharFile,
                                         MultipartFile policeFile,
                                         String userId,
                                         String gatePassId) {

        // Create directory path
        String directoryPath = ROOT_DIRECTORY + userId + "/"+gatePassId+"/";
        
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
            return "success";

        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
    }

    // Utility method to save file
    private void saveFile(MultipartFile file, String path) throws IOException {
        byte[] bytes = file.getBytes();
        Path filePath = Paths.get(path);
        Files.write(filePath, bytes);
    }
    
    @PostMapping("/saveGatePass")
    public ResponseEntity<String> saveGatePass(
            @RequestParam("jsonData") String jsonData,
            @RequestParam(value = "aadharFile", required = false) MultipartFile aadharFile,
            @RequestParam(value = "policeFile", required = false) MultipartFile policeFile,
            @RequestParam(value = "additionalFiles", required = false) List<MultipartFile> additionalFiles,
            @RequestParam(value = "documentTypes", required = false) List<String> documentTypes,
            HttpServletRequest request, HttpServletResponse response) {
        
        String gatePassId = null;
        GatePassMain gatePassMain;

        try {
            // Convert the JSON string back to the GatePassMain object
            ObjectMapper objectMapper = new ObjectMapper();
            gatePassMain = objectMapper.readValue(jsonData, GatePassMain.class);
            
            // Log the received GatePassMain object
            log.info("Received GatePassMain: {}", gatePassMain);

            gatePassMain.setCreatedBy(gatePassMain.getUserId());
            gatePassMain.setAadharDocName(aadharFile != null && !aadharFile.isEmpty() ? "aadhar":"");
            gatePassMain.setPoliceVerificationDocName(policeFile!=null && !policeFile.isEmpty() ? "police":"");
         // Mapping document types to their corresponding setter methods
            Map<String, Consumer<String>> docTypeSetterMap = new HashMap<>();
            docTypeSetterMap.put("Bank", gatePassMain::setBankDocName);
            docTypeSetterMap.put("Id2", gatePassMain::setIdProof2DocName);
            docTypeSetterMap.put("Other", gatePassMain::setOtherDocName);
            docTypeSetterMap.put("Medical", gatePassMain::setMedicalDocName);
            docTypeSetterMap.put("Education", gatePassMain::setEducationDocName);
            docTypeSetterMap.put("Training", gatePassMain::setTrainingDocName);
            docTypeSetterMap.put("Form11", gatePassMain::setForm11DocName);
            if(additionalFiles.size()>0) {
            // Set document names based on additionalFiles and documentTypes
            for (int i = 0; i < additionalFiles.size(); i++) {
                String docType = documentTypes.get(i);
                if (docType != null) {
                    Consumer<String> setter = docTypeSetterMap.get(docType);
                    if (setter != null) {
                        setter.accept(docType);
                    }
                }
            }
            }
            gatePassId = workmenService.saveGatePass(gatePassMain);

            if (gatePassId != null) {
                if (aadharFile != null && !aadharFile.isEmpty() && policeFile!=null && !policeFile.isEmpty()) {
                    uploadDocuments(aadharFile, policeFile, gatePassMain.getUserId(), gatePassId);
                }
                // Upload additional files
                if (additionalFiles != null && documentTypes != null) {
                    uploadAdditionalDocuments(additionalFiles, documentTypes, gatePassMain.getUserId(), gatePassId);
                }
                return new ResponseEntity<>("contractWorkmen/list", HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
        } catch (Exception e) {
            log.error("Error saving data: ", e);
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
    			listDto= workmenService.getGatePassListingDetails(user.getUserId(),GatePassType.CREATE.getStatus());
    		}else {
    			listDto = workmenService.getGatePassListingForApprovers(user,GatePassType.CREATE.getStatus());
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
         		return new ResponseEntity<>(result,HttpStatus.OK);
         	}
         	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                  .body("Error saving data: " + e.getMessage());
         } 
    }
    
    @GetMapping("/downloadFile/{gatePassId}/{userId}/{docType}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String gatePassId,@PathVariable String userId,  @PathVariable String docType) {
        try {
            // Construct the file path based on gatePassId and docType
            String filePath = ROOT_DIRECTORY+userId+"/" + gatePassId + "/" + docType + ".pdf";
            File file = new File(filePath);
            Resource resource = new FileSystemResource(file);

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    private String uploadAdditionalDocuments(List<MultipartFile> additionalFiles,
    		List<String> documentTypes,
    		String userId,
    		String gatePassId) {
    	// Create directory path
    	String directoryPath = ROOT_DIRECTORY + userId + "/" + gatePassId + "/";

    	try {
    		// Ensure the directory exists, if not create it
    		Path path = Paths.get(directoryPath);
    		if (!Files.exists(path)) {
    			Files.createDirectories(path);
    		}

    		for (int i = 0; i < additionalFiles.size(); i++) {
    			MultipartFile file = additionalFiles.get(i);
    			String docType = documentTypes.get(i);

    			// Create a filename based on the document type
    			String fileName = docType + ".pdf"; // or any other format you prefer
    			String filePath = directoryPath + fileName;

    			// Save the file
    			if (!file.isEmpty()) {
    				saveFile(file, filePath);
    			}
    		}

    		return "success";

    	} catch (IOException e) {
    		log.error("Failed to save additional documents: ", e);
    		return "failed";
    	}
    }
    
    
    @PostMapping("/gatePassAction")
    public ResponseEntity<String> gatePassAction(@RequestBody GatePassActionDto dto,HttpServletRequest request,HttpServletResponse response) {
    	String result=null; 
    	try {
             ObjectMapper objectMapper = new ObjectMapper();
             String gatePassActionDto = objectMapper.writeValueAsString(dto);
             log.info("Received gatePassActionDto JSON: {}", gatePassActionDto);
         } catch (Exception e) {
             log.error("Error converting gatePassActionDto to JSON", e);
         }
         try {
        	 result = workmenService.gatePassAction(dto);
         	if(null!=result) {
         		
         		if(dto.getGatePassType().equals(GatePassType.CREATE.getStatus())) {
            		result="contractWorkmen/view";
            	}else if(dto.getGatePassType().equals(GatePassType.CANCEL.getStatus()))
            	{
            		result="contractWorkmen/cancelView";
            	}else if(dto.getGatePassType().equals(GatePassType.BLOCK.getStatus()))
            	{
            		result="contractWorkmen/blockView";
            	}else if(dto.getGatePassType().equals(GatePassType.UNBLOCK.getStatus()))
            	{
            		result="contractWorkmen/unblockView";
            	}else if(dto.getGatePassType().equals(GatePassType.BLACKLIST.getStatus()))
            	{
            		result="contractWorkmen/blackView";
            	}else if(dto.getGatePassType().equals(GatePassType.DEBLACKLIST.getStatus()))
            	{
            		result="contractWorkmen/deblackView";
            	}else if(dto.getGatePassType().equals(GatePassType.LOSTORDAMAGE.getStatus()))
            	{
            		result="contractWorkmen/lostView";
            	}
         		return new ResponseEntity<>(result,HttpStatus.OK);
         	}
         	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                  .body("Error saving data: " + e.getMessage());
         } 
    }
    
    @GetMapping("/blockList")
    public String blockList(HttpServletRequest request,HttpServletResponse response) {
    	 HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	List<GatePassListingDto> listDto = new ArrayList<GatePassListingDto>();
    	if(null!=user) {
    		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			listDto= workmenService.getGatePassActionListingDetails(user.getUserId(),GatePassType.BLOCK.getStatus(),GatePassType.CREATE.getStatus());
    		}else {
    			listDto = workmenService.getGatePassListingForApprovers(user,GatePassType.BLOCK.getStatus());
    		}
    		request.setAttribute("contractorWorkmen", listDto);
    	}
    	 
    	return "contractWorkmen/blockListing";
    }
    
    @GetMapping("/unblockList")
    public String unblockList(HttpServletRequest request,HttpServletResponse response) {
    	 HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	List<GatePassListingDto> listDto = new ArrayList<GatePassListingDto>();
    	if(null!=user) {
    		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			listDto= workmenService.getGatePassActionListingDetails(user.getUserId(),GatePassType.UNBLOCK.getStatus(),
    					GatePassType.BLOCK.getStatus());
    		}else {
    			listDto = workmenService.getGatePassListingForApprovers(user,GatePassType.UNBLOCK.getStatus());
    		}
    		request.setAttribute("contractorWorkmen", listDto);
    	}
    	 
    	return "contractWorkmen/unblockListing";
    }
    
    @GetMapping("/blackList")
    public String blackList(HttpServletRequest request,HttpServletResponse response) {
    	 HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	List<GatePassListingDto> listDto = new ArrayList<GatePassListingDto>();
    	if(null!=user) {
    		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			listDto= workmenService.getGatePassActionListingDetails(user.getUserId(),GatePassType.BLACKLIST.getStatus(),GatePassType.CREATE.getStatus());
    		}else {
    			listDto = workmenService.getGatePassListingForApprovers(user,GatePassType.BLACKLIST.getStatus());
    		}
    		request.setAttribute("contractorWorkmen", listDto);
    	}
    	 
    	return "contractWorkmen/blackListing";
    }
    
    @GetMapping("/deblackList")
    public String deblackList(HttpServletRequest request,HttpServletResponse response) {
    	 HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	List<GatePassListingDto> listDto = new ArrayList<GatePassListingDto>();
    	if(null!=user) {
    		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			listDto= workmenService.getGatePassActionListingDetails(user.getUserId(),GatePassType.DEBLACKLIST.getStatus(),
    					GatePassType.BLACKLIST.getStatus());
    		}else {
    			listDto = workmenService.getGatePassListingForApprovers(user,GatePassType.DEBLACKLIST.getStatus());
    		}
    		request.setAttribute("contractorWorkmen", listDto);
    	}
    	 
    	return "contractWorkmen/deblackListing";
    }
    
    @GetMapping("/cancel")
    public String cancel(HttpServletRequest request,HttpServletResponse response) {
    	 HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	List<GatePassListingDto> listDto = new ArrayList<GatePassListingDto>();
    	if(null!=user) {
    		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			listDto= workmenService.getGatePassActionListingDetails(user.getUserId(),GatePassType.CANCEL.getStatus(),GatePassType.CREATE.getStatus());
    		}else {
    			listDto = workmenService.getGatePassListingForApprovers(user,GatePassType.CANCEL.getStatus());
    		}
    		request.setAttribute("contractorWorkmen", listDto);
    	}
    	 
    	return "contractWorkmen/cancelListing";
    }
    
    @GetMapping("/lostordamage")
    public String lostordamage(HttpServletRequest request,HttpServletResponse response) {
    	 HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	List<GatePassListingDto> listDto = new ArrayList<GatePassListingDto>();
    	if(null!=user) {
    		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			listDto= workmenService.getGatePassActionListingDetails(user.getUserId(),GatePassType.LOSTORDAMAGE.getStatus(),GatePassType.CREATE.getStatus());
    		}else {
    			listDto = workmenService.getGatePassListingForApprovers(user,GatePassType.LOSTORDAMAGE.getStatus());
    		}
    		request.setAttribute("contractorWorkmen", listDto);
    	}
    	 
    	return "contractWorkmen/lostListing";
    }
    
    @GetMapping("/cancelview/{gatePassId}")
    public String cancelviewIndividualContractWorkmenDetails(@PathVariable String gatePassId,HttpServletRequest request,HttpServletResponse response) {
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
    	
    		return "contractWorkmen/cancelView";
    	
    }
    @GetMapping("/blockview/{gatePassId}")
    public String blockviewIndividualContractWorkmenDetails(@PathVariable String gatePassId,HttpServletRequest request,HttpServletResponse response) {
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
    	
    		return "contractWorkmen/blockView";
    	
    }
    @GetMapping("/unblockview/{gatePassId}")
    public String unblockviewIndividualContractWorkmenDetails(@PathVariable String gatePassId,HttpServletRequest request,HttpServletResponse response) {
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
    	
    		return "contractWorkmen/unblockView";
    	
    }
    @GetMapping("/blackview/{gatePassId}")
    public String blackviewIndividualContractWorkmenDetails(@PathVariable String gatePassId,HttpServletRequest request,HttpServletResponse response) {
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
    	
    		return "contractWorkmen/blackView";
    	
    }
    
    @GetMapping("/deblackview/{gatePassId}")
    public String deblackviewIndividualContractWorkmenDetails(@PathVariable String gatePassId,HttpServletRequest request,HttpServletResponse response) {
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
    	
    		return "contractWorkmen/deblackView";
    	
    }
    @GetMapping("/lostordamageview/{gatePassId}")
    public String lostordamageviewIndividualContractWorkmenDetails(@PathVariable String gatePassId,HttpServletRequest request,HttpServletResponse response) {
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
    	
    		return "contractWorkmen/lostView";
    	
    }
    
}
