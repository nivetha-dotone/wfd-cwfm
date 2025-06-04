package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.ContractorComplianceDto;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ContractorService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/export")
public class ExportController {
	
	@Autowired
	WorkmenService workmenService;
	@Autowired
	CommonService commonService;
	@Autowired
	ContractorService contrService;

	@GetMapping("/list")
    public String list(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

		// Grouping the CmsGeneralMaster objects by gmType
		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

		// Define the types and their corresponding request attribute names
		Map<String, String> attributeMapping = Map.of(
		      				        "EXPORTOPTION","ExportOptions"
		      				      
		);

		// Iterate over the attribute mappings and set the request attributes dynamically
		attributeMapping.forEach((type, attributeName) -> {
		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
		    request.setAttribute(attributeName, gmList1);
		});

	   	List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
    	request.setAttribute("PrincipalEmployer", peList);
    	return "export/list";
	 }
	
	
	@GetMapping("/fetchModuleData")
    @ResponseBody
    public Map<String, Object> fetchModuleData(@RequestParam String module, @RequestParam String unitId) {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> rows = new ArrayList<>();
        List<String> columns = new ArrayList<>();

        switch (module) {
            case "Contractor":
                List<ContractorRegistration> contractors = contrService.getContractorMasterExportData(unitId);
                columns = Arrays.asList("CODE", "CONTRACTORNAME", "ADDRESS", "MOBILENO", "MANAGERNM", "EMAILADDR");
                for (ContractorRegistration c : contractors) {
                    Map<String, String> row = new LinkedHashMap<>();
                    row.put("CODE", c.getVendorCode());
                    row.put("CONTRACTORNAME", c.getContractorName());
                    row.put("ADDRESS", c.getAddress());
                    row.put("MOBILENO", c.getMobile());
                    row.put("MANAGERNM", c.getManagerName());
                    row.put("EMAILADDR", c.getEmail());
                    rows.add(row);
                }
                break;

            case "Contractor Compliance":
              List<ContractorComplianceDto> compliance = contrService.getContractorComplianceExportData(unitId);
              columns = Arrays.asList("PE ID", "Contractor Code", "License Number", "License Validity StartDate", "License Validity End Date", "Contractor Coverage",
            		  "Total Strength of contractor","Nature of Work","Contract Start Date","Contract End Date","ESI Number","WC/ESI Valid From","WC/ESI Valid To","WC No",
            		  "WC Valid From","WC Valid To","PF Number","PF Apply Date","Labor welfare Fund","Professional tax","GST Number","ISMW Applicability","Effective Date");
              for (ContractorComplianceDto c : compliance) {
                  Map<String, String> row = new LinkedHashMap<>();
                  row.put("PE ID",c.getPeId());
                  row.put("Contractor Code",c.getContractorCode());
                  row.put("License Number", c.getLicenseNumber());
                  row.put("License Validity StartDate", c.getLicenseStartDate());
                  row.put("License Validity End Date", c.getLicenseEndDate());
                  row.put("Contractor Coverage", c.getContractorCoverge());
                  row.put("Total Strength of contractor", c.getTotalStrength());
                  row.put("Nature of Work", c.getNatureOfWork());
                  row.put("Contract Start Date", c.getContractStartDate());
                  row.put("Contract End Date", c.getContractEndDate());
                  row.put("ESI Number", c.getEsiNumber());
                  row.put("WC/ESI Valid From", c.getWcEsiValidFrom());
                  row.put("WC/ESI Valid To", c.getWcEsiValidTo());
                  row.put("WC No", c.getWcNo());
                  row.put("WC Valid From", c.getWcValidFrom());
                  row.put("WC Valid To", c.getWcValidTo());
                  row.put("PF Number", c.getPfNumber());
                  row.put("PF Apply Date", c.getPfApplyDate());
                  row.put("Labor welfare Fund", c.getLaborWelfare());
                  row.put("Professional tax", c.getProfessionalTax());
                  row.put("GST Number", c.getGst());
                  row.put("ISMW Applicability", c.getIsmwApplicable());
                  row.put("Effective Date", c.getEffectiveDate());
                
                  rows.add(row);
              }
                break;
        }

        response.put("columns", columns);
        response.put("rows", rows);
        return response;
    }
}
