package com.wfd.dot1.cwfm.controller;

import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.service.FileUploadService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/data")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    // Display the form
    @GetMapping("/importExport")
    public String showUploadForm(HttpServletRequest request, HttpServletResponse response) {
    	
        return "reportsUpload/fileUpload";  
    }

	/*
	 * // Handle the file upload
	 * 
	 * @PostMapping("/uploadfile") public String
	 * handleFileUpload(@RequestParam("files") List<MultipartFile> files,
	 * RedirectAttributes redirectAttributes) { // Validate CSV file type for
	 * (MultipartFile file : files) { if
	 * (!file.getOriginalFilename().endsWith(".csv")) {
	 * redirectAttributes.addFlashAttribute("errorMessage",
	 * "Only CSV files are allowed!"); return "redirect:/upload"; // Redirect back
	 * to the form with error message } }
	 * 
	 * // If all files are valid CSV files, process them
	 * fileUploadService.uploadFiles(files);
	 * 
	 * // Add success message and redirect to the upload form
	 * redirectAttributes.addFlashAttribute("successMessage",
	 * "Files uploaded successfully!"); return "redirect:/upload"; // Redirect back
	 * to the form with success message }
	 */
    
	/*
	 * @GetMapping("/getPreviewData")
	 * 
	 * @ResponseBody public List<Map<String, String>> getPreviewData() { return
	 * fileUploadService.fetchCsvData(); // Fetch list of records from DB }
	 */
    
    @GetMapping("/getTemplateOptions")
    public ResponseEntity<String> getTemplateOptions(@RequestParam String selectedTemplate) {
        return ResponseEntity.ok("What would you like to do with this template?");
    }
    
    @GetMapping("/getTemplateInfo")
    public ResponseEntity<Map<String, Object>> getTemplateInfo(@RequestParam String templateType) {
    	 System.out.println("Received request for template: " + templateType); // Debugging
        Map<String, Object> templateInfo = new HashMap<>();

        if ("generalMaster".equals(templateType)) {
            templateInfo.put("title", "Data - General Master Event");
            templateInfo.put("description", "Imports GeneralMaster events.");

            List<Map<String, String>> fields = new ArrayList<>();
            fields.add(Map.of("name", "GM Name", "type", "Text", "example", "B.Tech"));
            fields.add(Map.of("name", "GM Description", "type", "Text", "example", "B.Tech"));
            fields.add(Map.of("name", "GM TypeID", "type", "Number", "example", "1"));
            fields.add(Map.of("name", "IS Active", "type", "Number", "example", "1"));
            fields.add(Map.of("name", "Created Time", "type", "Date", "example", "2024-04-17"));
            fields.add(Map.of("name", "Updated Time", "type", "Date", "example", "2024-04-17"));
            fields.add(Map.of("name", "Updated By", "type", "Text", "example", "Admin"));
			

            templateInfo.put("fields", fields);
        }else if("minimumWage".equals(templateType)){
        	templateInfo.put("title", "Data - Minimum Wage Event");
            templateInfo.put("description", "Imports Minimum Wage events.");

            List<Map<String, String>> fields = new ArrayList<>();
            fields.add(Map.of("name", "Trade", "type", "Text", "example", "Operator"));
            fields.add(Map.of("name", "Skill", "type", "Text", "example", "Skilled"));
            fields.add(Map.of("name", "Basic", "type", "Number", "example", "1000"));
            fields.add(Map.of("name", "Da", "type", "Number", "example", "0"));
            fields.add(Map.of("name", "Allowance", "type", "Number", "example", "1265"));
            fields.add(Map.of("name", "From Date", "type", "Date", "example", "2024-04-09"));
            fields.add(Map.of("name", "Unit Code", "type", "Text", "example", "112S"));
            fields.add(Map.of("name", "Organization", "type", "Text", "example", "Adani Wilmar Limited"));
            
            templateInfo.put("fields", fields);
        }else if("workorder".equals(templateType)){
        	templateInfo.put("title", "Data - Workorder Event");
            templateInfo.put("description", "Imports Workorder events.");

            List<Map<String, String>> fields = new ArrayList<>();
            fields.add(Map.of("name", "Work Order Number", "type", "Number", "example", "1511234563"));
            fields.add(Map.of("name", "Item", "type", "Number", "example", "12"));
            fields.add(Map.of("name", "Line", "type", "Number", "example", "10"));
            fields.add(Map.of("name", "Line Number", "type", "Number", "example", "10"));
            fields.add(Map.of("name", "Service Code", "type", "Number", "example", "998519"));
            fields.add(Map.of("name", "Short Text", "type", "Text", "example", "MANPOWER SUPPLY SERVICES"));
            fields.add(Map.of("name", "Delivery Complition", "type", "Date", "example", "2024-04-09"));
            fields.add(Map.of("name", "Item Changed ON", "type", "Date", "example", "2024-04-09"));
            fields.add(Map.of("name", "Vendor Code", "type", "Number", "example", "225046"));
            fields.add(Map.of("name", "Vendor Name", "type", "Text", "example", "SSD MANAGEMENT SERVICES PVT LTD"));
            fields.add(Map.of("name", "Vendor Address", "type", "Text", "example", "BLOCK-A| OFFICE NO. 711| MONDEAL HIGHTS| NEAR-PANCHRATNA PARTY PLOT"));
            fields.add(Map.of("name", "Blocked Vendor", "type", "Number", "example", "0"));
            fields.add(Map.of("name", "Work Order Validitiy From", "type", "Date", "example", "2024-04-09"));
            fields.add(Map.of("name", "Work Order Validitiy To", "type", "Date", "example", "2024-04-09"));
            fields.add(Map.of("name", "Work Order Type", "type", "Text", "example", "ZAS"));
            fields.add(Map.of("name", "Plant code", "type", "Number", "example", "121"));
            fields.add(Map.of("name", "Section Code", "type", "Number", "example", "5"));
            fields.add(Map.of("name", "Department Code", "type", "Number", "example", "4"));
            fields.add(Map.of("name", "G/L Code", "type", "Number", "example", "10"));
            fields.add(Map.of("name", "Cost Center", "type", "Number", "example", "51"));
            fields.add(Map.of("name", "Nature of Job", "type", "Text", "example", "MANPOWER SUPPLY SERVICES"));
            fields.add(Map.of("name", "Rate", "type", "Number", "example", "1"));
            fields.add(Map.of("name", "Quantity", "type", "Number", "example", "1"));
            fields.add(Map.of("name", "Base Unit of Measure", "type", "Number", "example", "1"));
            fields.add(Map.of("name", "Work Order Released", "type", "Date", "example", "2024-04-09"));
            fields.add(Map.of("name", "PM Order No", "type", "Number", "example", "11"));
            fields.add(Map.of("name", "WBS Element", "type", "Number", "example", "1000"));
            fields.add(Map.of("name", "Quantity Completed", "type", "Number", "example", "1"));
            fields.add(Map.of("name", "Work Order Release Date", "type", "Date", "example", "2024-04-09"));
            fields.add(Map.of("name", "Service Entry Created Date", "type", "Date", "example", "2024-04-09"));
            fields.add(Map.of("name", "Service Entry Updated Date", "type", "Date", "example", "2024-04-09"));
            fields.add(Map.of("name", "Purchase Org Level", "type", "Text", "example", "Department"));
            fields.add(Map.of("name", "Organization", "type", "Text", "example", "Adani Wilmar Limited"));
            
            templateInfo.put("fields", fields);
        }else if("contractor".equals(templateType)){
        	templateInfo.put("title", "Data - Contractor Event");
            templateInfo.put("description", "Imports Contractor events.");

            List<Map<String, String>> fields = new ArrayList<>();
            fields.add(Map.of("name", "Contractor Name", "type", "Text", "example", "SSD MANAGEMENT SERVICES PVT LTD"));
            fields.add(Map.of("name", "Contractor Address", "type", "Text", "example", "BLOCK-A| OFFICE NO. 711| MONDEAL HIGHTS| NEAR-PANCHRATNA PARTY PLOT| S.G HIGHWAY| AHMEDABAD|GUJARAT-380015."));
            fields.add(Map.of("name", "City", "type", "Text", "example", "Ahmedabad"));
            fields.add(Map.of("name", "Plant Code", "type", "Number", "example", "121"));
            fields.add(Map.of("name", "Contractor Manager Name", "type", "Text", "example", "Shubham Kumar"));
            fields.add(Map.of("name", "License Num", "type", "Text", "example", "FZR00CL7283"));
            fields.add(Map.of("name", "License Valid From", "type", "Date", "example", "01-01-2025"));
            fields.add(Map.of("name", "License Valid To", "type", "Date", "example", "31-12-2025"));
            fields.add(Map.of("name", "License Coverage", "type", "Number", "example", "80"));
            fields.add(Map.of("name", "Total Strength", "type", "Number", "example", "65"));
            fields.add(Map.of("name", "Maximum Number of Workmen", "type", "Number", "example", "70"));
            fields.add(Map.of("name", "Nature of Work", "type", "Text", "example", "Manpower supply"));
            fields.add(Map.of("name", "Location of Work", "type", "Text", "example", "FEROZEPUR"));
            fields.add(Map.of("name", "Contractor Validity Start Date", "type", "Date", "example", "01-04-2024"));
            fields.add(Map.of("name", "Contractor Validity End Date", "type", "Date", "example", "31-12-2025"));
            fields.add(Map.of("name", "Contractor Id", "type", "Number", "example", "225046"));
            fields.add(Map.of("name", "PF Code", "type", "Text", "example", "GJAHD2056438000"));
            fields.add(Map.of("name", "EC/WC Number", "type", "Text", "example", "4010/389322031/00/000"));
            fields.add(Map.of("name", "EC/WC Validity Start Date", "type", "Date", "example", "17-04-2025"));
            fields.add(Map.of("name", "EC/WC Validity End Date", "type", "Date", "example", "16-04-2026"));
            fields.add(Map.of("name", "Coverage", "type", "Number", "example", "80"));
            fields.add(Map.of("name", "PF Number", "type", "Text", "example", "GJAHD2056438000"));
            fields.add(Map.of("name", "PF Apply Date", "type", "Date", "example", "16-04-2026"));
            fields.add(Map.of("name", "Reference", "type", "Text", "example", "CODE001"));
            fields.add(Map.of("name", "Mobile Number", "type", "Number", "example", "9727606073"));
            fields.add(Map.of("name", "ESI Number", "type", "Text", "example", "4010/389322031/00/000"));
            fields.add(Map.of("name", "ESI Valid From", "type", "Date", "example", "17-04-2025"));
            fields.add(Map.of("name", "ESI Valid To", "type", "Date", "example", "16-04-2026"));
            fields.add(Map.of("name", "Organisation", "type", "Text", "example", "Adani Wilmar Ltd Ferozpur"));
            fields.add(Map.of("name", "Main Contractor Code", "type", "Number", "example", "225046"));
            fields.add(Map.of("name", "Work Order Number", "type", "Number", "example", "1511234563"));
            
            templateInfo.put("fields", fields);
        }else if("principalEmployer".equals(templateType)){
        	templateInfo.put("title", "Data - Principal Employer Event");
            templateInfo.put("description", "Imports PrincipalEmployer events.");

            List<Map<String, String>> fields = new ArrayList<>();
          //  fields.add(Map.of("name", "Unit ID", "type", "Number", "example", "1"));
            fields.add(Map.of("name", "Organization", "type", "Text", "example", "Dot1"));
            fields.add(Map.of("name", "Plant Code", "type", "Text", "example", "CODE001"));
            fields.add(Map.of("name", "Name", "type", "Text", "example", "Dot 1 Solutions Pvt Ltd"));
            fields.add(Map.of("name", "Address", "type", "Text", "example", "6th Floor|Aravalli Office|Shantigram|Nr. Vaishnodevi Circle"));
            fields.add(Map.of("name", "Manager Name", "type", "Text", "example", "Rama"));
            fields.add(Map.of("name", "Manager Address", "type", "Text", "example", "6th Floor|Aravalli Office|Shantigram|Nr. Vaishnodevi Circle"));
            fields.add(Map.of("name", "Bussiness Type", "type", "Text", "example", "Cement"));
            fields.add(Map.of("name", "Max Workmen", "type", "Number", "example", "15"));
            fields.add(Map.of("name", "Max Contract Workmen", "type", "Number", "example", "30"));
            fields.add(Map.of("name", "BOCW Applicability", "type", "Number", "example", "0"));
            fields.add(Map.of("name", "Is MW Applicability", "type", "Number", "example", "0"));
           // fields.add(Map.of("name", "Code", "type", "Text", "example", "CODE001"));
            fields.add(Map.of("name", "License Number", "type", "Number", "example", "LIC001"));
            fields.add(Map.of("name", "PF Code", "type", "Text", "example", "PF001"));
            fields.add(Map.of("name", "ESWC", "type", "Text", "example", "WC001"));
           // fields.add(Map.of("name", "PT REG NO", "type", "Text", "example", "PT001"));
          //  fields.add(Map.of("name", "LWF REG NO", "type", "Text", "example", "LWF001"));
            fields.add(Map.of("name", "Factory License Number", "type", "Text", "example", "FLIC001"));
           // fields.add(Map.of("name", "IS RC Applicable", "type", "Number", "example", "0"));
           // fields.add(Map.of("name", "RC Number", "type", "Number", "example", "RC001"));
           // fields.add(Map.of("name", "Attachment", "type", "File", "example", "Dot1_2025.pdf"));
            //fields.add(Map.of("name", "State ID", "type", "Number", "example", "1"));
           // fields.add(Map.of("name", "IS Active", "type", "Number", "example", "1"));
          //  fields.add(Map.of("name", "Update Time", "type", "Date", "example", "12/05/2025"));
          //  fields.add(Map.of("name", "Updated By", "type", "Date", "example", "12/05/2025"));
            
            templateInfo.put("fields", fields);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(templateInfo);
    }
    @GetMapping("/getTemplateData")
    public ResponseEntity<List<Map<String, String>>> getTemplateData(@RequestParam String templateType) {
    	 System.out.println(1);
        List<Map<String, String>> emptyData = new ArrayList<>(); // Returning empty data
        return ResponseEntity.ok(emptyData);
    }
    
    
    //@PostMapping("/uploadTemplate")
   // @ResponseBody
   // public ResponseEntity<Map<String, Object>> uploadTemplate(
      //      @RequestParam("file") MultipartFile file,
      //      @RequestParam("templateType") String templateType) {
       // try {
       //     Map<String, Object> result = fileUploadService.processTemplateFile(file, templateType);
        //    return ResponseEntity.ok(result);
      //  } catch (Exception e) {
         //   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
         //           "status", "error",
         //           "message", e.getMessage()
         //   ));
      ///  }
   // }
    @PostMapping("/uploadTemplate")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadTemplate(@RequestParam("file") MultipartFile file,
                                                              @RequestParam("templateType") String templateType) {
        try {
            Map<String, Object> result = fileUploadService.processTemplateFile(file, templateType);
            Map<String, Object> data = (Map<String, Object>) result.get("data");
           System.out.println(data);
            List<Map<String, Object>> successData = (List<Map<String, Object>>) data.get("successData");
            List<Map<String, Object>> errorData = (List<Map<String, Object>>) data.get("errorData");

            // Determine status and message based on result content
            if (!successData.isEmpty() && errorData.isEmpty()) {
                result.put("status", "success");
                result.put("message", "File uploaded and all rows saved successfully!");
            } else if (!successData.isEmpty() && !errorData.isEmpty()) {
                result.put("status", "partial");
                result.put("message", "File uploaded with some errors. Some rows saved.");
            } else if (successData.isEmpty() && !errorData.isEmpty()) {
                result.put("status", "error");
                result.put("message", "File upload failed. No valid rows to save.");
            } else {
                result.put("status", "error");
                result.put("message", "File is empty or invalid.");
            }

            result.put("templateType", templateType);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            e.printStackTrace();

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "An unexpected error occurred: " + e.getMessage());
            errorResponse.put("data", Map.of("successData", List.of(), "errorData", List.of()));

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/downloadTemplate")
    public void downloadTemplate(@RequestParam("templateType") String templateType,
                                 HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + templateType + "_template.csv");

        String csvContent = fileUploadService.getTemplateCSV(templateType);
        response.getWriter().write(csvContent);
    }
    }

    



