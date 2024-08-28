package com.wfd.dot1.cwfm.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.entity.CMSGMType;
import com.wfd.dot1.cwfm.entity.GeneralMaster;
import com.wfd.dot1.cwfm.entity.ReportPreferences;
import com.wfd.dot1.cwfm.entity.ReportPreferencesSelection;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ReportPreferencesService;
@Controller
@RequestMapping("/reportSetup")
public class ReportSetupController {

    @Autowired
    private ReportPreferencesService reportPreferencesService;
    @Autowired
	  private CommonService commonService;
    
//    
//    @GetMapping("/list")
//    public String showReportSetupPage(Model model) {
//        List<GeneralMaster> parametersList = reportPreferencesService.getAllParameters();
//        model.addAttribute("parametersList", parametersList);
//        return "reports/reportSetup";
//    }

    @GetMapping("/list")
    public String getReports(Model model) {
        model.addAttribute("reports", reportPreferencesService.getReportInfo());
        return "reports/reportSetup";
    }
    @PostMapping("/addReport")
    public String addReport(String reportName, String rdlName, String reportDescription, 
                            @RequestParam("selectedGmTypes[]") List<Long> selectedGmTypes) {
        // Your existing code to add the report preferences
    	 System.out.println("Report Name: " + reportName);
       System.out.println("RDL Name: " + rdlName);
       System.out.println("Report Description: " + reportDescription);
       System.out.println("Selected GM Types: " + selectedGmTypes);
        ReportPreferences reportPreferences = new ReportPreferences();
        reportPreferences.setRptName(reportName);
        reportPreferences.setRptLink(rdlName);
        reportPreferences.setRptDescription(reportDescription);
        reportPreferencesService.addReport(reportPreferences);
        for (Long gmTypeId : selectedGmTypes) {
            if (gmTypeId != null) {
                System.out.println("Selected GM gmTypeId: " + gmTypeId);
                ReportPreferencesSelection selection = new ReportPreferencesSelection();
                selection.setRptRefId(reportPreferences.getRptRefId());
                // Set the GM type ID directly as the RPTSELECTION
                selection.setRptSelection(gmTypeId);
                reportPreferencesService.addReportPreferenceSelection(selection);
            }
        }

//        for (Long gmTypeId : selectedGmTypes) {
//            if (gmTypeId != null) {
//                System.out.println("Selected GM gmTypeId: " + gmTypeId);
//                ReportPreferencesSelection selection = new ReportPreferencesSelection();
//                selection.setRptRefId(reportPreferences.getRptRefId());
//                // Check if the selected GM type ID exists in the CMSGENERALMASTER table
//                GeneralMaster generalMaster = reportPreferencesService.findById(gmTypeId);
//                if (generalMaster != null) {
//                    // Set the GMID as the RPTSELECTION
//                    selection.setRptSelection(gmTypeId);
//                    reportPreferencesService.addReportPreferenceSelection(selection);
//                } else {
//                    // Handle the case where the GMType with the given ID is not found
//                    // You might throw an exception, display an error message, or handle it differently based on your requirements
//                    return "redirect:/error"; // Redirect to an error page
//                }
//            }
//        }
        // Redirect to a success page or return a success message
        return "redirect:/reportSetup/list"; // Redirect to a success page
    }
    @GetMapping("/reports")
    public String showReports(Model model) {
    	List<GeneralMaster> parametersList = reportPreferencesService.getAllParameters();
//      model.addAttribute("parametersList", parametersList);
    	model.addAttribute("reports", parametersList);

        // Return the name of the JSP file to render
        return "reports"; // Assuming your JSP file name is reports.jsp
    }
//    @GetMapping("/getReports")
//    public String getReportList(Model model) {
//        model.addAttribute("reports", reportPreferencesService.getReportInfo());
//        return "reports/reportSetup";
//    }
    
    @GetMapping("/getReports")
    public @ResponseBody List<ReportPreferences> getReportList() {
        return reportPreferencesService.getReportList();
    }
//    @GetMapping("/loadReport")
//    @ResponseBody
//    public String loadReport(@RequestParam("reportName") String reportName) {
//        // Fetch report data from the database based on the report name
//        ReportData reportData = reportPreferencesService.getReportData(reportName);
//        
//        // Process the report data and return as HTML or JSON response
//        String htmlResponse = generateHTMLReport(reportData);
//        
//        return htmlResponse;
//    }
    @GetMapping("/reportPreferences")
    public String showReportPreferences(@RequestParam("reportRefId") Long reportRefId, Model model) {
//        List<ReportPreferences> reportPreferences = reportPreferencesService.getAllReportPreferencesWithParameters(reportRefId);
//        model.addAttribute("reportPreferences", reportPreferences);
        List<Object[]> rows = reportPreferencesService.getAllReportPreferencesWithReportId(reportRefId);
        model.addAttribute("reportRows", rows);
        return "reports/reportRun"; // Replace "reportPreferencesPage" with your actual view name
    }
//    @GetMapping("/fetchReportParameters")
//    @ResponseBody
//    public List<Object[]> fetchReportParameters(@RequestParam("reportRefId") Long reportRefId) {
//        // Fetch the report parameters based on the selected report reference ID
//        List<Object[]> reportParameters = reportPreferencesService.getAllReportPreferencesWithReportId(reportRefId);
//        return reportParameters;
//    }
//    @GetMapping("/fetchReportParameters")
//    public String fetchReportParameters(@RequestParam("reportRefId") Long reportRefId, Model model) {
//        // Fetch the report parameters based on the selected report reference ID
//        List<Object[]> reportParameters = reportPreferencesService.getAllReportPreferencesWithReportId(reportRefId);
//        
//        // Add the reportParameters list as a model attribute
//        model.addAttribute("reportParameters", reportParameters);
//        
//        // Return the name of the JSP file to be rendered
//        return "redirect:/reports/reportRun";
//    }
    
    @GetMapping("/fetchReportParameters")
    public String fetchReportParameters(@RequestParam("reportRefId") Long reportRefId, Model model) {
        // Fetch the report parameters based on the selected report reference ID
        List<Object[]> reportParameters = reportPreferencesService.getAllReportPreferencesWithReportId(reportRefId);
        
        // Split the report parameters where necessary
        for (Object[] parameter : reportParameters) {
            String parameterType = (String) parameter[5];
            if ("UnitCode".equals(parameterType) || "Department".equals(parameterType)) {
                // Check if parameter array length is greater than 6 before accessing index 6
                if (parameter.length > 6) {
                    String parameterValues = (String) parameter[6];
                    parameter[6] = parameterValues.split("\n");
                }
            }
        }
        
        // Add the modified reportParameters list as a model attribute
        model.addAttribute("reportParameters", reportParameters);
        
        // Return the name of the JSP file to be rendered
        return "reports/reportRun";
    }
//    @GetMapping("/fetchReportParameters")
//    public String fetchReportParameters(@RequestParam("reportRefId") Long reportRefId, Model model) {
//        // Fetch the report details including parameter types and values based on the selected report reference ID
//        List<Object[]> reportDetails = reportPreferencesService.getAllReportPreferencesWithReportId(reportRefId);
//        
//        // Extract report name, description, and parameter types
//        String reportName = (String) reportDetails.get(0)[0];
//        String reportDescription = (String) reportDetails.get(0)[1];
//        List<String> parameterTypes = new ArrayList<>();
//        for (Object[] detail : reportDetails) {
//            String parameterType = (String) detail[5];
//            if (!parameterTypes.contains(parameterType)) {
//                parameterTypes.add(parameterType);
//            }
//        }
//
//        // Fetch parameter values for each parameter type
//        Map<String, List<String>> parameterValuesMap = new HashMap<>();
//        for (String parameterType : parameterTypes) {
//            List<String> parameterValues = reportPreferencesService.getParameterValuesByType(reportRefId, parameterType);
//            parameterValuesMap.put(parameterType, parameterValues);
//        }
//
//        // Add the extracted details to the model
//        model.addAttribute("reportName", reportName);
//        model.addAttribute("reportDescription", reportDescription);
//        model.addAttribute("parameterTypes", parameterTypes);
//        model.addAttribute("parameterValuesMap", parameterValuesMap);
//
//        // Return the name of the JSP file to be rendered
//        return "reports/reportRun";
//    }
    public List<String> getReportParameters(Long reportId) {
        List<Object[]> reportPreferences = reportPreferencesService.getAllReportPreferencesWithReportId(reportId);
        List<String> parameters = new ArrayList<>();

        for (Object[] preference : reportPreferences) {
            // Extract parameter name and value from each preference
            String parameterName = (String) preference[2]; // Assuming ParameterName is at index 2
            String parameterValue = (String) preference[4]; // Assuming ParameterType is at index 4
            parameters.add(parameterName + ": " + parameterValue);
        }

        return parameters;
    }
//    @GetMapping("/reportPreferences")
//    public String showReportPreferences(Model model) {
//        List<ReportPreferences> reportPreferences = reportPreferencesService.getAllReportPreferences();
//        model.addAttribute("reportPreferences", reportPreferences);
//        return "reportPreferences";
//    }

//    @GetMapping("/add")
//    public String addReportFronList(Model model) {
//    	  List<GeneralMaster> parametersList = reportPreferencesService.getAllParameters();
//        model.addAttribute("parametersList", parametersList);
//        return "reports/add";
//    }
//    @PostMapping("/addReport")
//    public String addReport(String reportName, String rdlName, String reportDescription, 
//                            @RequestParam("selectedGmTypes") List<Long> selectedGmTypes) {
//        // Your existing code to add the report preferences
//        ReportPreferences reportPreferences = new ReportPreferences();
//        System.out.println("Report Name: " + reportName);
//      System.out.println("RDL Name: " + rdlName);
//      System.out.println("Report Description: " + reportDescription);
//      System.out.println("Selected GM Types: " + selectedGmTypes);
//        reportPreferences.setRptName(reportName);
//        reportPreferences.setRptLink(rdlName);
//        reportPreferences.setRptDescription(reportDescription);
//        reportPreferencesService.addReport(reportPreferences);
//        for (Long gmTypeId : selectedGmTypes) {
//            if (gmTypeId != null) {
//            	System.out.println("Selected GM gmTypeId: " + gmTypeId);
//                ReportPreferencesSelection selection = new ReportPreferencesSelection();
//                selection.setRptRefId(reportPreferences.getRptRefId());
//                CMSGMType gmType = commonService.findById(gmTypeId);
//                selection.setRptSelection(gmTypeId);
//                reportPreferencesService.addReportPreferenceSelection(selection);
////                if (gmType != null) {
////                    selection.setRptSelection(gmTypeId);
////                    reportPreferencesService.addReportPreferenceSelection(selection);
////                } else {
////                    // Handle the case where the GMType with the given ID is not found
////                    // You might throw an exception, display an error message, or handle it differently based on your requirements
////                    return "redirect:/error"; // Redirect to an error page
////                }
//            }
//        }

//        for (Integer gmTypeId : selectedGmTypes) {
//            ReportPreferencesSelection selection = new ReportPreferencesSelection();
//            selection.setRptRefId(reportPreferences.getRptRefId());
//            // Assuming you have a service to retrieve the GMType by its ID
//            CMSGMType gmType = commonService.findById(gmTypeId);
//            selection.setRptSelection(Long.valueOf(gmType.getGmTypeId()));// Set the GMType ID as RPTSELECTION
//            reportPreferencesService.addReportPreferenceSelection(selection);
//        }
        // Now, handle the selected gmTypes and save them accordingly
//        for (Long gmTypeId : selectedGmTypes) {
//            ReportPreferencesSelection selection = new ReportPreferencesSelection();
//            selection.setRptRefId(reportPreferences.getRptRefId());
//            selection.setRptSelection(gmTypeId); // Assuming gmTypeId is the ID of the selected GM type
//            reportPreferencesService.addReportPreferenceSelection(selection);
//        }

//        return "redirect:/reportSetup/list";
//    }

//    @PostMapping("/addReport")
//    public String addReport(String reportName, String rdlName, String reportDescription, 
//                            @RequestParam("selectedGmTypes") List<String> selectedGmTypes) {
//        ReportPreferences reportPreferences = new ReportPreferences();
//        selectedGmTypes.removeIf(String::isEmpty);
//        System.out.println("Report Name: " + reportName);
//        System.out.println("RDL Name: " + rdlName);
//        System.out.println("Report Description: " + reportDescription);
//        System.out.println("Selected GM Types: " + selectedGmTypes);
//        reportPreferences.setRptName(reportName);
//        reportPreferences.setRptLink(rdlName);
//        reportPreferences.setRptDescription(reportDescription);
//        
//        // Save the ReportPreferences object using your service
//        reportPreferencesService.addReport(reportPreferences);
//        
//        // Now, handle the selected gmTypes and save them accordingly
//        for (String gmType : selectedGmTypes) {
//            ReportPreferencesSelection selection = new ReportPreferencesSelection();
//            selection.setRptRefId(reportPreferences.getRptRefId());
//            selection.setRptSelection(Long.valueOf(gmType));
//            reportPreferencesService.addReportPreferenceSelection(selection);
//        }
//        
//        return "redirect:/reportSetup/list";
//    }


//    
//    @PostMapping("/addReport")
//    public String addReport(@RequestParam String reportName,
//                            @RequestParam String rdlName,
//                            @RequestParam String reportDescription,
//                            @RequestParam("gmType") List<Long> gmTypeIds) {
//        ReportPreferences report = new ReportPreferences();
//        report.setRptName(reportName);
//        report.setRptLink(rdlName);
//        report.setRptDescription(reportDescription);
//        // Set the selected GM types
//        reportPreferencesService.addReport(report);
//        
//     // Save the selected parameters for this report preference
//        for (Long gmTypeId : gmTypeIds) {
//        	ReportPreferencesSelection selection = new ReportPreferencesSelection();
//            selection.setRptRefId(report.getRptRefId()); // Assuming you have a getId() method in ReportPreferences
//            selection.setRptSelection(gmTypeId); // Set the selected parameter ID
//            selection.setUpdatedDtm(LocalDateTime.now()); // Set the current timestamp
//            reportPreferencesService.addReportPreferenceSelection(selection);
//        }
//        return "redirect:/reportSetup/list"; // Redirect to the appropriate URL
//    }

    
//    @PostMapping("/addReport")
//    public String addReport(String reportName, String rdlName, String reportDescription, String parameters) {
//        ReportPreferences report = new ReportPreferences();
//        report.setRptName(reportName);
//        report.setRptLink(rdlName);
//        report.setRptDescription(reportDescription);
//        // Assuming parameters are stored as a string, you may need to parse it appropriately
//        report.setParameters(parameters);
//        reportPreferencesService.addReport(report);
//        return "redirect:reports/reportSetup";
//    }
    @GetMapping("/add")
    public String addReportFronList(Model model) {
    	  List<CMSGMType> gmTypes = reportPreferencesService.getAllGmTypes();
    	    model.addAttribute("gmTypes", gmTypes);
//        List<GeneralMaster> parametersList = reportPreferencesService.getAllParameters();
//        List<String> parameterIds = parametersList.stream()
//                                                  .map(parameter -> String.valueOf(parameter.getGmid()))
//                                                  .collect(Collectors.toList());
//        model.addAttribute("parameterIds", parameterIds);
//        model.addAttribute("parametersList", parametersList);
        return "reports/add";
    }
}

