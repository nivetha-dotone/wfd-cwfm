package com.wfd.dot1.cwfm.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wfd.dot1.cwfm.entity.CMSPrincipalEmployer;
import com.wfd.dot1.cwfm.entity.State;
import com.wfd.dot1.cwfm.service.CMSPRINCIPALEMPLOYERService;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.validator.PrincipalEmployerValidator;
@Controller
@RequestMapping("/principalEmployer")
public class PrincipalEmployerController {
    private final CMSPRINCIPALEMPLOYERService cmSPRINCIPALEMPLOYERService;
    private final PrincipalEmployerValidator PrincipalEmployerValidator; // Injected validator
    @Autowired
    private CommonService commonService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PrincipalEmployerController.class);
    @Autowired
    public PrincipalEmployerController(CMSPRINCIPALEMPLOYERService cmSPRINCIPALEMPLOYERService,
    		PrincipalEmployerValidator PrincipalEmployerValidator) {
this.cmSPRINCIPALEMPLOYERService = cmSPRINCIPALEMPLOYERService;
this.PrincipalEmployerValidator = PrincipalEmployerValidator;
}

//    @RequestMapping("/list")
//    public String listEmployees(@RequestParam(defaultValue = "1") int page,
//                                @RequestParam(defaultValue = "5") String pageSize,
//                                Model model) {
//        int pageSizeValue = pageSize.equalsIgnoreCase("All") ? Integer.MAX_VALUE : Integer.parseInt(pageSize);
//        
//        // Retrieve paginated data based on page and pageSize
//        List<CMSPrincipalEmployer> employees = cmSPRINCIPALEMPLOYERService.getAllCMSPRINCIPALEMPLOYERs(page, pageSizeValue);
//
//        // Calculate total number of pages
//        int totalRecords = cmSPRINCIPALEMPLOYERService.getTotalRecords();
//        int totalPages = (int) Math.ceil((double) totalRecords / pageSizeValue);
//
//        // Add data to the model
//        model.addAttribute("cmSPRINCIPALEMPLOYERs", employees);
//        model.addAttribute("totalPages", totalPages);
//        model.addAttribute("currentPage", page);
//        return "principalEmployer/list";
//    }
//    @RequestMapping("/list")
//    public String listEmployees(@RequestParam(required = false) Long lastSeenId,
//                                @RequestParam(defaultValue = "5") int pageSize,
//                                Model model) {
//        // Fetch employees based on the last seen ID and page size
//        List<CMSPrincipalEmployer> employees = cmSPRINCIPALEMPLOYERService.getEmployeesAfterId(lastSeenId, pageSize);
//
//        // Add data to the model
//        model.addAttribute("cmSPRINCIPALEMPLOYERs", employees);
//        return "principalEmployer/list";
//    }
//    @RequestMapping("/list")
//    public String getAllCMSPRINCIPALEMPLOYERs(@RequestParam(defaultValue = "1") int page, Model model) {
//        int pageSize = 5; // Number of records per page
//
//        // Call your service method to fetch the data based on pagination parameters
//        List<CMSPrincipalEmployer> cmSPRINCIPALEMPLOYERs = cmSPRINCIPALEMPLOYERService.getAllCMSPRINCIPALEMPLOYERs(page, pageSize);
//
//        // Calculate total number of records and total number of pages
//        int totalRecords = cmSPRINCIPALEMPLOYERService.getTotalRecords();
//        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
//
//        // Pass data to the view
//        model.addAttribute("cmSPRINCIPALEMPLOYERs", cmSPRINCIPALEMPLOYERs);
//        model.addAttribute("totalPages", totalPages);
//        model.addAttribute("currentPage", page);
//
//        return "principalEmployer/list"; // Assuming cmsprincipalemployerlist.jsp is the view name
//    }

    @RequestMapping("/list")
    public String getAllCMSPRINCIPALEMPLOYERs(Model model) {
        List<CMSPrincipalEmployer> cmSPRINCIPALEMPLOYERs = cmSPRINCIPALEMPLOYERService.getAllCMSPRINCIPALEMPLOYERs();
        model.addAttribute("cmSPRINCIPALEMPLOYERs", cmSPRINCIPALEMPLOYERs);
        return "principalEmployer/list"; // Assuming cmsprincipalemployerlist.jsp is the view name
    }
    
    @GetMapping("/view/{id}")
    public String viewPrincipalEmployers(@PathVariable Long id,Model model) {
    	CMSPrincipalEmployer principalEmployer = cmSPRINCIPALEMPLOYERService.getCMSPRINCIPALEMPLOYERById(id);
        model.addAttribute("principalEmployer", principalEmployer);
        return "principalEmployer/view";
    }

    @GetMapping("/add")
    public String addPrincipalEmployerForm(Model model) {
        model.addAttribute("principalEmployer", new CMSPrincipalEmployer());
        List<State> states = commonService.getAllStates(); // Fetch list of states from the service
        model.addAttribute("states", states);
        return "principalEmployer/add";
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addPrincipalEmployer(HttpServletRequest request, @ModelAttribute("principalEmployer") CMSPrincipalEmployer principalEmployer, 
             @RequestParam("file") MultipartFile file, BindingResult result, Model model) {
         principalEmployer.setATTACHMENTNM(file.getOriginalFilename());
         int selectedStateId = Integer.parseInt(request.getParameter("selectedState"));
         principalEmployer.setSTATEID(selectedStateId);
         List<State> states = commonService.getAllStates();
         model.addAttribute("states", states);
        PrincipalEmployerValidator.validate(principalEmployer, result);
        
//        int errorsLength = result.getErrorCount(); // Assuming errors is a BindingResult object
//        model.addAttribute("errorsLength", errorsLength);
        if (result.hasErrors()) {
        	model.addAttribute("errors", result);
            return "principalEmployer/add"; // Return to the form page if validation fails
        }
       
        // Handle file upload
        if (!file.isEmpty()) {
            try {
                // Get original file name and extension
                String originalFilename = file.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                System.out.println("Received file: " + file.getOriginalFilename());
                // Generate timestamp
                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                String timestamp = currentTime.format(formatter);

                // Construct file name with extension
                String fileNameWithExtension = principalEmployer.getNAME() + "_" + timestamp + fileExtension;

                // Construct directory path
                String directoryPath = "C:\\CWFM\\PrincipalEmployer\\" + principalEmployer.getCODE() + "\\";

                // Create directory if it doesn't exist
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Construct file path
                String filePath = directoryPath + fileNameWithExtension;

                // Save file to disk
                file.transferTo(new File(filePath));

                // Store file name in the database entity
                principalEmployer.setATTACHMENTNM(fileNameWithExtension);

                // Print statements for debugging
                System.out.println("File uploaded successfully: " + fileNameWithExtension);
                System.out.println("File path: " + filePath);

            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
                // Return an error response or perform other actions as needed
                System.err.println("Error occurred while handling file upload: " + e.getMessage());
            }
        }
       
        // Now you can save the principalEmployer object to the database
        cmSPRINCIPALEMPLOYERService.addCMSPRINCIPALEMPLOYER(principalEmployer);
        return "redirect:/principalEmployer/list";
    }
    @GetMapping("/viewFile")
    public ResponseEntity<FileSystemResource> viewFile(@RequestParam("unitCode") String unitCode, @RequestParam("fileName") String fileName) {
        // Construct file path based on unit code and file name
        String filePath = "C:\\CWFM\\PrincipalEmployer\\" + unitCode + "\\" + fileName;
        System.err.println("Error occurred while handling file filePath: " + filePath);
        // Load the file as a resource
        FileSystemResource resource = new FileSystemResource(new File(filePath));

        // Set headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName); // Force download with the provided file name
        long contentLength = 0;// = resource.contentLength();
        try {
             contentLength = resource.contentLength();
            // Handle content length
        } catch (IOException e) {
            // Handle the IOException appropriately, for example:
            e.printStackTrace();
        }
        // Return response entity with file content
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(contentLength)
                .body(resource);
    }
//    @RequestMapping(value = "/viewFile", method = RequestMethod.GET)
//    public void viewFile(@RequestParam("unitCode") String unitCode, @RequestParam("fileName") String fileName, HttpServletResponse response) {
//        // Construct the file path based on the unit code and file name
//        String filePath = "C:\\CWFM\\PrincipalEmployer\\" + unitCode + "\\" + fileName;
//
//        // Code to read the file and send it to the response
//        try (InputStream inputStream = new FileInputStream(filePath)) {
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//
//            IOUtils.copy(inputStream, response.getOutputStream());
//            response.flushBuffer();
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle the exception appropriately
//        }
//    }

//    @RequestMapping(path = "/add", method = RequestMethod.POST)
//    public String addPrincipalEmployer(@ModelAttribute("principalEmployer") CMSPRINCIPALEMPLOYER principalEmployer, BindingResult result) {
//    	PrincipalEmployerValidator.validate(principalEmployer, result);
//        if (result.hasErrors()) {
//            return "principalEmployer/add"; // Return to the form page if validation fails
//        }
//
//        LOGGER.info("Received request to add principal employer with ID: {}",principalEmployer.getADDRESS());
//        cmSPRINCIPALEMPLOYERService.addCMSPRINCIPALEMPLOYER(principalEmployer);
//        return "redirect:/principalEmployer/list";
//    }
   // @PostMapping("/add")
//    @RequestMapping(path = "/add", method = RequestMethod.POST)
//    public String addPrincipalEmployer(@ModelAttribute("principalEmployer") CMSPRINCIPALEMPLOYER principalEmployer) {
//    	 LOGGER.info("Received request to add principal employer with ID: {}",principalEmployer.getADDRESS());
//    	cmSPRINCIPALEMPLOYERService.addCMSPRINCIPALEMPLOYER(principalEmployer);
//        return "redirect:/principalEmployer/list";
//    }
    @GetMapping("/search")
    public String searchPrincipalEmployers(@RequestParam("query") String query, Model model) {
        List<CMSPrincipalEmployer> searchResults = cmSPRINCIPALEMPLOYERService.searchCMSPRINCIPALEMPLOYERs(query);
        model.addAttribute("cmSPRINCIPALEMPLOYERs", searchResults);
        return "principalEmployer/list";
    }
//    @GetMapping("/principalEmployer/add")
//    public String showAddPrincipalEmployerForm(Model model) {
//        model.addAttribute("principalEmployer", new PrincipalEmployer());
//        return "addPrincipalEmployer";
//    }
//
//    @PostMapping("/principalEmployer/add")
//    public String addPrincipalEmployer(@ModelAttribute("principalEmployer") PrincipalEmployer principalEmployer) {
//        principalEmployerService.addPrincipalEmployer(principalEmployer);
//        return "redirect:/principalEmployer/list";
//    }
    
    @GetMapping("/edit/{id}")
    public String editPrincipalEmployerForm(@PathVariable Long id, Model model) {
        CMSPrincipalEmployer principalEmployer = cmSPRINCIPALEMPLOYERService.getCMSPRINCIPALEMPLOYERById(id);
        model.addAttribute("principalEmployer", principalEmployer);
        List<State> states = commonService.getAllStates();
        model.addAttribute("states", states);
        return "principalEmployer/edit";
    }
    
//    @RequestMapping(path = "/edit/{id}", method = RequestMethod.POST)
//    public String editPrincipalEmployer(HttpServletRequest request, @PathVariable("id") Long id,
//            @ModelAttribute("principalEmployer") CMSPRINCIPALEMPLOYER principalEmployer,
//            @RequestParam("file") MultipartFile file, BindingResult result, Model model) {

		/*
		 * @RequestMapping(path = "/edit/{id}", method = RequestMethod.POST) public
		 * String editPrincipalEmployer(HttpServletRequest request,@PathVariable Long
		 * id, @ModelAttribute("principalEmployer") CMSPRINCIPALEMPLOYER
		 * principalEmployer, BindingResult result, Model model) {
		 */
    
    
    @RequestMapping(path = "/edit/{id}", method = RequestMethod.POST)
    public String editPrincipalEmployer(HttpServletRequest request, @PathVariable String id,
			@ModelAttribute("principalEmployer") CMSPrincipalEmployer principalEmployer/*
																						 * ,@RequestParam("file")
																						 * MultipartFile file
																						 */, BindingResult result, Model model) {
     
    	principalEmployer.setUNITID(Long.parseLong(id));
    	int selectedStateId = Integer.parseInt(request.getParameter("selectedState"));
        principalEmployer.setSTATEID(selectedStateId);
        List<State> states = commonService.getAllStates();
        model.addAttribute("states", states);
    	PrincipalEmployerValidator.validate(principalEmployer, result);
        if (result.hasErrors()) {
        	model.addAttribute("errors", result);
            return "principalEmployer/edit"; // Return to the form page if validation fails
        }
//        if (file != null && !file.isEmpty()) {
//            try {
//                // Get original file name and extension
//                String originalFilename = file.getOriginalFilename();
//                principalEmployer.setATTACHMENTNM(file.getOriginalFilename());
//                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
//                System.out.println("Received file: " + file.getOriginalFilename());
//                // Generate timestamp
//                LocalDateTime currentTime = LocalDateTime.now();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//                String timestamp = currentTime.format(formatter);
//
//                // Construct file name with extension
//                String fileNameWithExtension = principalEmployer.getNAME() + "_" + timestamp + fileExtension;
//
//                // Construct directory path
//                String directoryPath = "C:\\CWFM\\PrincipalEmployer\\" + principalEmployer.getCODE() + "\\";
//
//                // Create directory if it doesn't exist
//                File directory = new File(directoryPath);
//                if (!directory.exists()) {
//                    directory.mkdirs();
//                }
//
//                // Construct file path
//                String filePath = directoryPath + fileNameWithExtension;
//
//                // Save file to disk
//                file.transferTo(new File(filePath));
//
//                // Store file name in the database entity
//                principalEmployer.setATTACHMENTNM(fileNameWithExtension);
//
//                // Print statements for debugging
//                System.out.println("File uploaded successfully: " + fileNameWithExtension);
//                System.out.println("File path: " + filePath);
//
//            } catch (IOException e) {
//                e.printStackTrace(); // Handle the exception appropriately
//                // Return an error response or perform other actions as needed
//                System.err.println("Error occurred while handling file upload: " + e.getMessage());
//            }
//        }
        // Update the principal employer
        cmSPRINCIPALEMPLOYERService.updateCMSPRINCIPALEMPLOYER(principalEmployer);

        // Redirect to the view page
        return "redirect:/principalEmployer/list";
    }
    //@PostMapping("/edit/{id}")
//    @RequestMapping(path = "/edit/{id}", method = RequestMethod.POST)
//    public String editPrincipalEmployer(@PathVariable Long id, @ModelAttribute CMSPRINCIPALEMPLOYER principalEmployer) {
//        LOGGER.info("Received request to edit principal employer with ID: {}", id);
//        LOGGER.info("Received request to edit principal employer with ID: {}", principalEmployer.getADDRESS());
//        // Set the unit ID
//        principalEmployer.setUNITID(id);
//        
//        // Update the principal employer
//        cmSPRINCIPALEMPLOYERService.updateCMSPRINCIPALEMPLOYER(principalEmployer);
//        
//        LOGGER.info("Principal employer updated successfully: {}", principalEmployer);
//        
//        // Redirect to the view page
//        return "redirect:/principalEmployer/list";
//    }
   
	/*
	 * @PostMapping("/edit/{id}") public String editPrincipalEmployer(@PathVariable
	 * Long id, @ModelAttribute("principalEmployer") CMSPRINCIPALEMPLOYER
	 * principalEmployer) { principalEmployer.setUNITID(id);
	 * cmSPRINCIPALEMPLOYERService.updateCMSPRINCIPALEMPLOYER(principalEmployer);
	 * return "redirect:/principalEmployer/view"; }
	 */
}
