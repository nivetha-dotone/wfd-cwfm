package com.wfd.dot1.cwfm.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wfd.dot1.cwfm.pojo.BillVerification;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.service.BillVerificationService;
import com.wfd.dot1.cwfm.service.ContractorService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkmenService;

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

    		List<BillVerification> listDto = billService.getBillVerificationList(user.getUserId());
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
}
