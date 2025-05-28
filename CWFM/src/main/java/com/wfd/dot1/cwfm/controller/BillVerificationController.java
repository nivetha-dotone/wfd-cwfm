package com.wfd.dot1.cwfm.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dto.CMSWageCostDTO;
import com.wfd.dot1.cwfm.dto.ChecklistItemDTO;
import com.wfd.dot1.cwfm.pojo.BillVerification;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.service.BillConfigService;
import com.wfd.dot1.cwfm.service.BillVerificationService;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ContractorService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/billVerification")
public class BillVerificationController {
	
	@Autowired
	BillVerificationService billService;
	
	@Autowired
	WorkmenService workmenService;

	@Autowired
	PrincipalEmployerService peService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
    private BillConfigService billConfigService;

	@Autowired
	ContractorService contrService;
	private static final String ROOT_DIRECTORY = "C:/wfd_cwfm/ep_docs/";
	@GetMapping("/viewlist")
    public String billVerification(HttpServletRequest request,HttpServletResponse response) {
    	 
    	return "bill/billverification";
	 }
    @GetMapping("/List")
    public String getbillList(HttpServletRequest request, HttpServletResponse response) {

    		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
    		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    		List<BillVerification> listDto = billService.getBillVerificationList(String.valueOf(user.getUserId()));
    		request.setAttribute("billlist", listDto);
    		return "bill/list";

    	
    }
    @GetMapping("/billview/{transactionId}")
	public String viewbillDetails(@PathVariable String transactionId, HttpServletRequest request,
			HttpServletResponse response) {
    	BillVerification bill = billService.viewbillDetails(transactionId);
		request.setAttribute("billverification", bill);
		BillVerification reports = billService.viewbillReportsDetails(transactionId);
		request.setAttribute("billreports", reports);
		BillVerification hrclearance = billService.viewbillhrclearanceDetails(transactionId);
		request.setAttribute("billhrclearance", hrclearance);
		BillVerification precomments = billService.viewbillprecomments(transactionId);
		request.setAttribute("billprecomments", precomments);
		return "bill/view"; // Return the JSP file name
	}
    @GetMapping("/downloadFile/{docType}")
    public ResponseEntity<Resource> downloadFile(  @PathVariable("docType") String docType) {
        try {
        	String filePath=null;
        	if(docType.equals("musterroll")||docType.equals("billverification")||docType.equals("wagecost")||docType.equals("bonusreport")||docType.equals("bankstatement")||docType.equals("annualreturn")||docType.equals("challanpt")||docType.equals("userattachment1")||docType.equals("userattachment2")
        			||docType.equals("extrahours")||docType.equals("forma")||docType.equals("formb")||docType.equals("formc")||docType.equals("formd")||docType.equals("ecrpf")||docType.equals("challanpf")||docType.equals("ecresic")||docType.equals("challanesic")||docType.equals("userattachment3")||docType.equals("bonusregister")||docType.equals("lwfchallan"))
        	{
        		// Construct the file path based on gatePassId and docType
                filePath = ROOT_DIRECTORY+ "/" + docType + ".pdf";
        	}else {
                filePath = ROOT_DIRECTORY+ "/" + docType;
        	}
        	
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
    
    @GetMapping("/createBill")
    public String createBill(HttpServletRequest request,HttpServletResponse response) {
	
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
        String id = billService.generateWCTransactionId();
		request.setAttribute("transactionId", id);
		List<PrincipalEmployer> peList = billService.getPEDetailByUser(user.getUserAccount());
		request.setAttribute("peList", peList);
		
		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

		// Grouping the CmsGeneralMaster objects by gmType
		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

		// Define the types and their corresponding request attribute names
		Map<String, String> attributeMapping = Map.of(
		        "BILL TYPE","BillType",
		        "BILL CATEGORY","BillCategory",
		        "CHECKLIST STATUS","ChecklistStatus"
		);

		// Iterate over the attribute mappings and set the request attributes dynamically
		attributeMapping.forEach((type, attributeName) -> {
		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
		    request.setAttribute(attributeName, gmList1);
		});

		request.setAttribute("kronosReports", billConfigService.fetchKronosReportsWithId());
		request.setAttribute("statutoryReports", billConfigService.fetchStatutoryReportsWithId());
		request.setAttribute("checklistItems", billConfigService.getAllChecklistItems());
		
        return "bill/add";
    }
    
    @PostMapping("/saveBill")
    public ResponseEntity<String> saveBill(
        @RequestParam("jsonData") String jsonData,
        @RequestParam("checklistJson") String checklistJson,
        @RequestParam Map<String, MultipartFile> files
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            CMSWageCostDTO workflowData = mapper.readValue(jsonData, CMSWageCostDTO.class);
            List<ChecklistItemDTO> checklistItems = Arrays.asList(
                mapper.readValue(checklistJson, ChecklistItemDTO[].class)
            );

            // Save core data
            //billService.save(workflowData, checklistItems, files);

            return ResponseEntity.ok("Saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Save failed");
        }
    }

}
