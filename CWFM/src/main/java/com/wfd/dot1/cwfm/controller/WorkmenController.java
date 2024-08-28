package com.wfd.dot1.cwfm.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.wfd.dot1.cwfm.entity.CMSContractor;
import com.wfd.dot1.cwfm.entity.CMSPerson;
import com.wfd.dot1.cwfm.entity.CMSPrincipalEmployer;
import com.wfd.dot1.cwfm.entity.GeneralMaster;
import com.wfd.dot1.cwfm.entity.State;
import com.wfd.dot1.cwfm.service.CMSPRINCIPALEMPLOYERService;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ContractorService;

@Controller
@RequestMapping("/contractworkmen")
public class WorkmenController {
	@Autowired
    private CMSPRINCIPALEMPLOYERService principalEmployerService;
	@Autowired
    private ContractorService contractorService;
  @Autowired
  private CommonService commonService;
  private final RestTemplate restTemplate = new RestTemplate();
  String apiUrl = "https://sandbox.surepass.io/api/v1/aadhaar-validation/aadhaar-validation";
  String authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTcxMjIxMDIxOCwianRpIjoiY2NhZDM2MjktODU3YS00NmI5LTg0NjUtYjJhYjU1Njg5ZmQ1IiwidHlwZSI6ImFjY2VzcyIsImlkZW50aXR5IjoiZGV2LmNvbnNvbGVfMnlsYnl5bmFmamI0MWZqZjJidTZuYm1rZTFxQHN1cmVwYXNzLmlvIiwibmJmIjoxNzEyMjEwMjE4LCJleHAiOjE3MTMwNzQyMTgsInVzZXJfY2xhaW1zIjp7InNjb3BlcyI6WyJ1c2VyIl19fQ.bVA-ORbsGKIFLec3GL7QAu-J_tCY-BCS8hl1ZJV9NKQ";

  private final String AADHAR_API_URL = "https://sandbox.surepass.io/api/v1/aadhaar-validation/aadhaar-validation";

  @RequestMapping("/contractWorkmenList")
  public String getAllWorkmenbyPEidandContid(Model model,
          @RequestParam(required = false) Long principalEmployerId,
          @RequestParam(required = false) Long contractorId) {
      
      // Get all principal employers
      List<CMSPrincipalEmployer> principalEmployers = principalEmployerService.getAllCMSPRINCIPALEMPLOYERs();
      model.addAttribute("principalEmployers", principalEmployers);
      
      if (principalEmployerId != null) {
          model.addAttribute("selectedPrincipalEmployerId", principalEmployerId);
          
          // Get contractors by principal employer ID
          List<CMSContractor> contList = contractorService.getContractorsByPrincipalEmployerId(principalEmployerId);
          model.addAttribute("contractors", contList);
          
          if (contractorId != null) {
              model.addAttribute("selectedContractorId", contractorId);
              
              
              // Get persons (workmen) based on principal employer and contractor IDs
              List<CMSPerson> persons = commonService.getAllPersonsByPrincipalEmployerAndContractor(principalEmployerId, contractorId);
              model.addAttribute("persons", persons);
          }
      }
      
      return "contractWorkmen/list"; // Assuming cmsprincipalemployerlist.jsp is the view name
  }
  
  //	 @RequestMapping("/aadharOnbordingList")
//	    public String getAllCMSPRINCIPALEMPLOYERs(Model model,@RequestParam(required = false) Long principalEmployerId,@RequestParam(required = false)  Long contractorId) {
//	        List<CMSPrincipalEmployer> principalEmployers = principalEmployerService.getAllCMSPRINCIPALEMPLOYERs();
//	        model.addAttribute("principalEmployers", principalEmployers);
//	        if (principalEmployerId != null) {
//	            model.addAttribute("selectedPrincipalEmployerId", principalEmployerId);
//	            List<CMSContractor> contList = contractorService.getContractorsByPrincipalEmployerId(principalEmployerId);
//	            model.addAttribute("contractors", contList);
//	            model.addAttribute("selectedContractorId", contractorId);
//	            List<CMSWorkOrder> woList = commonService.getWorkOrdersByContractorIdAndUnitId(principalEmployerId,contractorId);
//	            model.addAttribute("woList", woList);
//	            
//	            List<CMSPerson> persons = commonService.getAllPersons();
//		        model.addAttribute("persons", persons);
////	            CMSContractor contractor = contractorService.getContractorById(contractorId);
////		        model.addAttribute("contractor", contractor);
//	        }
//	       
//	        return "contractWorkmen/aadharList"; // Assuming cmsprincipalemployerlist.jsp is the view name
//	    } 
	 @RequestMapping("/aadharOnbordingList")
	    public String getAllCMSPRINCIPALEMPLOYERs(Model model,
	            @RequestParam(required = false) Long principalEmployerId,
	            @RequestParam(required = false) Long contractorId) {
	        
	        // Get all principal employers
	        List<CMSPrincipalEmployer> principalEmployers = principalEmployerService.getAllCMSPRINCIPALEMPLOYERs();
	        model.addAttribute("principalEmployers", principalEmployers);
	        
	        if (principalEmployerId != null) {
	            model.addAttribute("selectedPrincipalEmployerId", principalEmployerId);
	            
	            // Get contractors by principal employer ID
	            List<CMSContractor> contList = contractorService.getContractorsByPrincipalEmployerId(principalEmployerId);
	            model.addAttribute("contractors", contList);
	            
	            if (contractorId != null) {
	                model.addAttribute("selectedContractorId", contractorId);
	                
	                
	                // Get persons (workmen) based on principal employer and contractor IDs
	                List<CMSPerson> persons = commonService.getAllPersonsByPrincipalEmployerAndContractor(principalEmployerId, contractorId);
	                model.addAttribute("persons", persons);
	            }
	        }
	        
	        return "contractWorkmen/aadharList"; // Assuming cmsprincipalemployerlist.jsp is the view name
	    }
	 
	 @RequestMapping("/aadharOnbordingLists")
	    public String getAllCMSPRINCIPALEMPLOYERs(Model model) {
	        
	        // Get all principal employers
	        List<CMSPrincipalEmployer> principalEmployers = principalEmployerService.getAllCMSPRINCIPALEMPLOYERs();
	        model.addAttribute("principalEmployers", principalEmployers);
	        List<CMSContractor> contList = contractorService.getAllContractors();
            model.addAttribute("contractors", contList);
	        List<CMSPerson> persons = commonService.getAllPersons();
	        model.addAttribute("persons", persons);
//	        if (principalEmployerId != null) {
//	            model.addAttribute("selectedPrincipalEmployerId", principalEmployerId);
//	            
//	            // Get contractors by principal employer ID
//	            List<CMSContractor> contList = contractorService.getContractorsByPrincipalEmployerId(principalEmployerId);
//	            model.addAttribute("contractors", contList);
//	            
//	            if (contractorId != null) {
//	                model.addAttribute("selectedContractorId", contractorId);
//	                
//	                
//	                // Get persons (workmen) based on principal employer and contractor IDs
//	                List<CMSPerson> persons = commonService.getAllPersonsByPrincipalEmployerAndContractor(principalEmployerId, contractorId);
//	                model.addAttribute("persons", persons);
//	            }
//	        }
	        
	        return "contractWorkmen/aadharList"; // Assuming cmsprincipalemployerlist.jsp is the view name
	    }
	 @GetMapping("/addAadharOB")
	    public String addWorkmenAadharOBForm(Model model) {
	        model.addAttribute("workmenbyAadhar", new CMSPerson());
	        List<State> states = commonService.getAllStates(); // Fetch list of states from the service
	        model.addAttribute("states", states);
	        List<GeneralMaster> bloodGroupOptions = commonService.getGeneralMasterOptionsByName("BLOODGROUP");
	        model.addAttribute("bloodGroupOptions", bloodGroupOptions);
	        return "contractWorkmen/aadharOBAdd";
	    }

	    @RequestMapping(path = "/addAadharOB", method = RequestMethod.POST)
	    public String addWorkmenAadharOB(HttpServletRequest request, @ModelAttribute("workmenbyAadhar") CMSPerson person, 
	              BindingResult result, Model model) {
	        
	    	 // person.setEmployeeId(request.getParameter("employeeId"));
	    	    person.setEmployeeCode(request.getParameter("employeeCode"));
	    	    person.setFirstName(request.getParameter("firstName"));
	    	    person.setRelationName(request.getParameter("relationName"));
	    	    person.setLastName(request.getParameter("lastName"));
	    	    String dateOfBirthStr = request.getParameter("dateOfBirth");
	    	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the date format as per your requirement
	    	    Date dateOfBirth = null;
	    	    String dateOfJoiningStr = request.getParameter("dateOfJoining");
	    	    String dateOfTerminationStr = request.getParameter("dateOfTermination");

	    	    Date dateOfJoining = null;
	    	    Date dateOfTermination = null;
	    	    try {
	    	        dateOfBirth = dateFormat.parse(dateOfBirthStr);
	    	        dateOfJoining = dateFormat.parse(dateOfJoiningStr);
	    	        dateOfTermination = dateFormat.parse(dateOfTerminationStr);
	    	    } catch (ParseException e) {
	    	        e.printStackTrace(); // Handle parsing exception as per your application's error handling strategy
	    	    }
	    	    person.setDateOfBirth(dateOfBirth);
	    	    person.setDateOfJoining(dateOfJoining);
	    	    person.setDateOfTermination(dateOfTermination);
	    	//    person.setBloodGroup(request.getParameter("bloodGroup"));
	    	    person.setHazardousArea(request.getParameter("hazardousArea"));
	    	 //   person.setGender(request.getParameter("gender"));
//	    	    person.setAcademics(request.getParameter("academics"));
//	    	    person.setAccomodation(request.getParameter("accomodation"));
	    	    person.setBankBranch(request.getParameter("bankBranch"));
	    	    person.setAccountNo(request.getParameter("accountNo"));
	    	    person.setEmergencyName(request.getParameter("emergencyName"));
	    	    person.setEmergencyNumber(request.getParameter("emergencyNumber"));
	    	    person.setMobileNumber(request.getParameter("mobileNumber"));
	    	  //  person.setAccessLevel(request.getParameter("accessLevel"));
	    	    person.setEsicNumber(request.getParameter("esicNumber"));
	    	    person.setUanNumber(request.getParameter("uanNumber"));
	    	  //  person.setIspfEligible(request.getParameter("ispfEligible"));
	    	    person.setIdMark(request.getParameter("idMark"));
	    	    person.setPanNo(request.getParameter("panno"));
	    	    person.setUnitId(request.getParameter("unitId"));
	    	    person.setDepartmentId(request.getParameter("departmentId"));
	    	    person.setContractorId(request.getParameter("contractorId"));
	    	    person.setSkillId(request.getParameter("skillId"));
	    	    person.setTradeId(request.getParameter("tradeId"));
	    	    person.setAadharNumber(request.getParameter("aadharNumber"));
	        // Now you can save the principalEmployer object to the database
	    //	commonService.addPerson(person);
	        return "redirect:/contractWorkmen/aadharList";
	    }
	    @RequestMapping("/quickOnbordingList")
	    public String getAllquickOnbordingList(Model model) {
	        
	        // Get all principal employers
	        List<CMSPrincipalEmployer> principalEmployers = principalEmployerService.getAllCMSPRINCIPALEMPLOYERs();
	        model.addAttribute("principalEmployers", principalEmployers);
	        List<CMSContractor> contList = contractorService.getAllContractors();
            model.addAttribute("contractors", contList);
	        List<CMSPerson> persons = commonService.getAllPersons();
	        model.addAttribute("persons", persons);
	        
	        return "contractWorkmen/quickOBList"; // Assuming cmsprincipalemployerlist.jsp is the view name
	    }
	    @GetMapping("/addQuickOB")
	    public String addQuickOBForm(Model model) {
	        model.addAttribute("workmenbyAadhar", new CMSPerson());
	        List<State> states = commonService.getAllStates(); // Fetch list of states from the service
	        model.addAttribute("states", states);
	        List<GeneralMaster> bloodGroupOptions = commonService.getGeneralMasterOptionsByName("BLOODGROUP");
	        model.addAttribute("bloodGroupOptions", bloodGroupOptions);
	        return "contractWorkmen/quickOBAdd";
	    }

	    @RequestMapping(path = "/addQuickOB", method = RequestMethod.POST)
	    public String addWorkmenQuickOB(HttpServletRequest request, @ModelAttribute("workmenbyAadhar") CMSPerson person, 
	              BindingResult result, Model model) {
	        
	    	 // person.setEmployeeId(request.getParameter("employeeId"));
	    	    person.setEmployeeCode(request.getParameter("employeeCode"));
	    	    person.setFirstName(request.getParameter("firstName"));
	    	    person.setRelationName(request.getParameter("relationName"));
	    	    person.setLastName(request.getParameter("lastName"));
	    	    String dateOfBirthStr = request.getParameter("dateOfBirth");
	    	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the date format as per your requirement
	    	    Date dateOfBirth = null;
	    	    String dateOfJoiningStr = request.getParameter("dateOfJoining");
	    	    String dateOfTerminationStr = request.getParameter("dateOfTermination");

	    	    Date dateOfJoining = null;
	    	    Date dateOfTermination = null;
	    	    try {
	    	        dateOfBirth = dateFormat.parse(dateOfBirthStr);
	    	        dateOfJoining = dateFormat.parse(dateOfJoiningStr);
	    	        dateOfTermination = dateFormat.parse(dateOfTerminationStr);
	    	    } catch (ParseException e) {
	    	        e.printStackTrace(); // Handle parsing exception as per your application's error handling strategy
	    	    }
	    	    person.setDateOfBirth(dateOfBirth);
	    	    person.setDateOfJoining(dateOfJoining);
	    	    person.setDateOfTermination(dateOfTermination);
	    	//    person.setBloodGroup(request.getParameter("bloodGroup"));
	    	    person.setHazardousArea(request.getParameter("hazardousArea"));
	    	 //   person.setGender(request.getParameter("gender"));
//	    	    person.setAcademics(request.getParameter("academics"));
//	    	    person.setAccomodation(request.getParameter("accomodation"));
	    	    person.setBankBranch(request.getParameter("bankBranch"));
	    	    person.setAccountNo(request.getParameter("accountNo"));
	    	    person.setEmergencyName(request.getParameter("emergencyName"));
	    	    person.setEmergencyNumber(request.getParameter("emergencyNumber"));
	    	    person.setMobileNumber(request.getParameter("mobileNumber"));
	    	  //  person.setAccessLevel(request.getParameter("accessLevel"));
	    	    person.setEsicNumber(request.getParameter("esicNumber"));
	    	    person.setUanNumber(request.getParameter("uanNumber"));
	    	  //  person.setIspfEligible(request.getParameter("ispfEligible"));
	    	    person.setIdMark(request.getParameter("idMark"));
	    	    person.setPanNo(request.getParameter("panno"));
	    	    person.setUnitId(request.getParameter("unitId"));
	    	    person.setDepartmentId(request.getParameter("departmentId"));
	    	    person.setContractorId(request.getParameter("contractorId"));
	    	    person.setSkillId(request.getParameter("skillId"));
	    	    person.setTradeId(request.getParameter("tradeId"));
	    	    person.setAadharNumber(request.getParameter("aadharNumber"));
	        // Now you can save the principalEmployer object to the database
	    //	commonService.addPerson(person);
	        return "redirect:/contractWorkmen/quickOBList";
	    }
	    
	    @PostMapping("/submitAadhar")
	    public String submitAadhar(String aadharNumber, Model model) {
	        // Here you can process the Aadhar number, validate it, and initiate OTP generation
	        // For simplicity, let's assume we just print the Aadhar number
	        System.out.println("Aadhar Number: " + aadharNumber);

	        // Simulate calling Aadhar API to retrieve data using the token
	        String aadharData = getDataFromAadharAPI(apiUrl, authToken);

	        if (aadharData != null && !aadharData.isEmpty()) {
	            // Pass retrieved data to the JSP
	            model.addAttribute("aadharData", aadharData);

	            // Return the JSP page to display retrieved Aadhar data
	            return "aadharDataPage";
	        } else {
	            // Handle data retrieval failure
	            model.addAttribute("error", "Failed to retrieve Aadhar data from API");
	            return "errorPage";
	        }
	    }

	    // Method to retrieve data from Aadhar API using token
	    private String getDataFromAadharAPI(String apiUrl, String authToken) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("Authorization", "Bearer " + authToken); // Using the provided auth token

	        // Making GET request to retrieve data
	        ResponseEntity<String> response = restTemplate.exchange(
	            apiUrl + "/getData",
	            HttpMethod.GET,
	            new HttpEntity<>(headers),
	            String.class
	        );

	        if (response.getStatusCode() == HttpStatus.OK) {
	            // Assuming the retrieved data is returned as part of the response body
	            return response.getBody();
	        } else {
	            // Handle data retrieval failure
	            return null;
	        }
	    }
	    // Method to get token from Aadhar API
	    private String getTokenFromAadharAPI(String aadharNumber) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // Assuming Aadhar API requires Aadhar number as a JSON object
	        String requestBody = "{\"aadharNumber\": \"" + aadharNumber + "\"}";
	        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

	        // Making POST request to generate token
	        String tokenResponse = restTemplate.exchange(
	            AADHAR_API_URL + "/generateToken",
	            HttpMethod.POST,
	            request,
	            String.class
	        ).getBody();
	        
	        // Assuming the token is returned as part of the response
	        return tokenResponse; // You may need to parse the response to get the token
	    }
	    
	    // Method to retrieve data from Aadhar API using token
	    private String getDataFromAadharAPI(String token) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("Authorization", "Bearer " + token); // Assuming Aadhar API requires token in Authorization header

	        // Making GET request to retrieve data
	        String aadharData = restTemplate.exchange(
	            AADHAR_API_URL + "/getData",
	            HttpMethod.GET,
	            new HttpEntity<>(headers),
	            String.class
	        ).getBody();
	        
	        // Assuming the retrieved data is returned as part of the response
	        return aadharData; // You may need to parse the response to get the Aadhar data
	    }
	    
//	    @GetMapping("/aadharForm")
//	    public String showAadharForm(Model model) {
//	        model.addAttribute("aadharModel", new AadharModel());
//	        return "aadharForm";
//	    }
//
//	    @PostMapping("/validateAadhar")
//	    public String validateAadhar(@ModelAttribute("aadharModel") AadharModel aadharModel, Model model) {
//	        // Validate Aadhar and send OTP
//	        boolean isValidAadhar = aadharService.validateAadhar(aadharModel.getAadharNumber());
//	        if (isValidAadhar) {
//	            model.addAttribute("otpModel", new OTPModel());
//	            return "otpForm";
//	        } else {
//	            // Handle invalid Aadhar
//	            return "errorPage";
//	        }
//	    }
//
//	    @PostMapping("/validateOTP")
//	    public String validateOTP(@ModelAttribute("otpModel") OTPModel otpModel, Model model) {
//	        // Validate OTP
//	        boolean isValidOTP = aadharService.validateOTP(otpModel.getOTP());
//	        if (isValidOTP) {
//	            // Fetch employee details using Aadhar number
//	            EmployeeDetails employeeDetails = employeeService.fetchEmployeeDetails(otpModel.getAadharNumber());
//	            model.addAttribute("employeeDetailsModel", employeeDetails);
//	            return "employeeDetailsPage";
//	        } else {
//	            // Handle invalid OTP
//	            return "errorPage";
//	        }
//	    }
}
