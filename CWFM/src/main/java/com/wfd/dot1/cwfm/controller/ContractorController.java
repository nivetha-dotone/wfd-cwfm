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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.ContractorRenewal;
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
	public String getAllPrincipalEmployer(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		List<PrincipalEmployer> peList = workmenService.getAllPrincipalEmployer(user.getUserAccount());
		request.setAttribute("principalEmployers", peList);

		return "contractors/list";
	}
//	@GetMapping("/list")
//	public String getAllPrincipalEmployer(@RequestParam(required = false) Long pageId, @RequestParam(required = false) Long selectedRoleId, HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession(false); 
//		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
//		 if (pageId != null) {
//		        session.setAttribute("selectedPageId", pageId);
//		    } else {
//		        pageId = (Long) session.getAttribute("selectedPageId");
//		    }
//		    
//		   // Long selectedRoleId = (Long) session.getAttribute("selectedRoleId");
//		    System.out.println("pageId---"+pageId);
//		    System.out.println("selectedRoleId---"+selectedRoleId);
//		List<PrincipalEmployer> peList = workmenService.getAllPrincipalEmployer(String.valueOf(user.getUserId()));
//		request.setAttribute("principalEmployers", peList);
//
//		List<CMSRoleRights> userRights = commonService.getRoleRightsByRoleIdAndPageId(selectedRoleId, pageId); // 10029 = Contractor Page ID
//	    request.setAttribute("userRights", userRights);
//	    Enumeration<String> attributeNames = session.getAttributeNames();
//	    while (attributeNames.hasMoreElements()) {
//	        String attributeName = attributeNames.nextElement();
//	        System.out.println(attributeName + ": " + session.getAttribute(attributeName));
//	    }
//		return "contractors/list";
//	}


	@PostMapping("/getAllContractorsBasedOnPE")
	public ResponseEntity<List<Contractor>> searchResources(
			@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

			// Sample data (replace with your database queries)
			List<Contractor> contractorList = workmenService.getAllContractorBasedOnPE(principalEmployerId,
					user.getUserAccount());

			if (contractorList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(contractorList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/view/{contractorId}")
	public String viewContractor(@PathVariable("contractorId") String contractorId,
			@RequestParam("principalEmployerId") String principalEmployerId, HttpServletRequest request,
			HttpServletResponse response) {

		// Use contractorId and principalEmployerId to fetch data as needed
		Contractor contractor = contrService.getContractorById(contractorId);
		request.setAttribute("contractor", contractor);
		PrincipalEmployer principalEmployer = peService.getIndividualPEDetailByUnitId(principalEmployerId);
		request.setAttribute("principalEmployer", principalEmployer);

		CMSContrPemm contractorPEMM = contrService.getMappingByContractorIdAndUnitId(contractorId, principalEmployerId);
		request.setAttribute("contractorPEMM", contractorPEMM);

		List<CmsContractorWC> laborLicenses = contrService
				.getMappingsByContractorIdAndUnitIdAndLicenseType(contractorId, principalEmployerId, "LL");
		request.setAttribute("laborLicenses", laborLicenses);

		List<String> licenseTypes = Arrays.asList("WC", "ESIC");
		List<CmsContractorWC> contractorWCList = contrService
				.getMappingsByContractorIdAndUnitIdAndLicenseTypes(contractorId, principalEmployerId, licenseTypes);
		request.setAttribute("contractorWCList", contractorWCList);

		List<Workorder> workOrderList = contrService.getWorkOrdersByContractorIdAndUnitId(contractorId,
				principalEmployerId);
		request.setAttribute("workOrderList", workOrderList);

		return "contractors/view";
	}

	@GetMapping("/contReg")
	public String getContractorRegistration(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		List<PrincipalEmployer> peList = workmenService.getAllPrincipalEmployer(String.valueOf(user.getUserId()));
		request.setAttribute("PrincipalEmployer", peList);

		return "contractors/contractorRegistration";
	}

	@PostMapping("/saveReg")
	public ResponseEntity<String> saveReg(@RequestBody ContractorRegistration contreg, HttpServletRequest request,
			HttpServletResponse response) {
		String result = contrService.saveReg(contreg);
		return new ResponseEntity<>("contractors/contractorRegistrationList", HttpStatus.OK);
	}

	/*
	 * public class TransactionIdServlet extends HttpServlet { protected void
	 * doGet(HttpServletRequest request, HttpServletResponse response) throws
	 * ServletException, IOException { // Generate a unique contractorID using
	 * current timestamp (you can modify this logic) long contractorID =
	 * System.currentTimeMillis(); // Unique ID based on timestamp
	 * 
	 * // Set the contractorID attribute in the request
	 * request.setAttribute("contractorID", contractorID);
	 * 
	 * // Forward the request to the JSP page RequestDispatcher dispatcher =
	 * request.getRequestDispatcher("contractorRegistration.jsp");
	 * dispatcher.forward(request, response); }
	 */

	@GetMapping("/contRegList")
	public String getContractorRegistrationList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		List<ContractorRegistration> listDto = contrService.getContractorRegistrationList(String.valueOf(user.getUserId()));
		request.setAttribute("contractorlist", listDto);
		return "contractors/contractorRegistrationList";

	}

	@GetMapping("/contractorview/{contractorregId}")
	public String viewContractorDetails(@PathVariable String contractorregId, HttpServletRequest request,
			HttpServletResponse response) {
		ContractorRegistration contractor = contrService.viewContractorDetails(contractorregId);
		request.setAttribute("principalEmployer", contractor);
		List<ContractorRegistration> list = contrService.viewContractorAddDetails(contractorregId);
		request.setAttribute("additionalDetails", list);
		return "contractors/contractorView"; // Return the JSP file name
	}

	@GetMapping("/contRenewal")
	public String getContractorRenewal(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		return "contractors/contractorRenewal";
	}

	@GetMapping("/contRenewalList")
	public String getContractorRenewalList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		List<ContractorRegistration> listDto = contrService.getContractorRenewalList(String.valueOf(user.getUserId()));
		request.setAttribute("contractorlist", listDto);
		return "contractors/contractorRenewList";

	}
	@GetMapping("/contractorRenewalview/{contractorRenewId}")
	public String viewContractorRenewDetails(@PathVariable String contractorRenewId, HttpServletRequest request,
			HttpServletResponse response) {
		ContractorRenewal renew = contrService.viewContractorRenewDetails(contractorRenewId);
		request.setAttribute("contractRenew", renew);
		List<ContractorRenewal> list = contrService.viewContractorRenewAddDetails(contractorRenewId);
		request.setAttribute("renewDetails", list);
		return "contractors/contractorRenewalView"; // Return the JSP file name
	}
	@GetMapping("/role")
	public String getRole(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		/*
		 * List<PrincipalEmployer> peList = workmenService.getRole(user.getUserId());
		 * request.setAttribute("PrincipalEmployer", peList);
		 */
		return "contractors/role";
	}
	@GetMapping("/roleList")
	public String getRoleList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		List<MasterUser> listDto = contrService.getRoleList(String.valueOf(user.getUserId()));
		request.setAttribute("contractorlist", listDto);
		return "contractors/rolelist";

	}
	@PostMapping("/saveRole")
	public ResponseEntity<String> saveRole(@RequestBody MasterUser user, HttpServletRequest request,
			HttpServletResponse response) {
		String result = contrService.saveRole(user);
		return new ResponseEntity<>("contractors/rolelist", HttpStatus.OK);
	}
	@PostMapping("/saveRenew")
	public ResponseEntity<String> saveRenew(@RequestBody ContractorRenewal contrenew, HttpServletRequest request,
			HttpServletResponse response) {
		String result = contrService.saveRenew(contrenew);
		return new ResponseEntity<>("contractors/contractorRegistrationList", HttpStatus.OK);
	}
	@GetMapping("/generateContractorId")
    public String generateContractorId(Model model) {
        String contractorregId = contrService.generateUniqueContractorId();
        model.addAttribute("contractorregId", contractorregId); // Pass the ID to the view
        return "contractorRegistration"; // JSP page name
    }

}
