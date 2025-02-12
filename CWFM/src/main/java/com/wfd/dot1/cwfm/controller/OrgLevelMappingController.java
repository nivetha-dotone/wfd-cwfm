package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wfd.dot1.cwfm.dao.OrgLevelDao;
import com.wfd.dot1.cwfm.dto.OrgLevelDefDTO;
import com.wfd.dot1.cwfm.dto.OrgLevelEntryDTO;
import com.wfd.dot1.cwfm.pojo.OrgLevelMapping;
import com.wfd.dot1.cwfm.service.OrgLevelMappingService;
import com.wfd.dot1.cwfm.service.OrgLevelService;

@Controller
@RequestMapping("/org-level-mapping")
public class OrgLevelMappingController {
	@Autowired
    private OrgLevelService orgLevelService;
	@Autowired
    private OrgLevelDao orgLevelDao;
    @Autowired
    private OrgLevelMappingService orgLevelMappingService;

	/*
	 * @GetMapping("/list") public String listMappings(Model model) {
	 * List<OrgLevelMapping> mappings = orgLevelMappingService.listMappings();
	 * model.addAttribute("mappings", mappings); return "orgLevelMapping/list"; }
	 */

    @GetMapping("/list")
    public String loadOrgLevelPage(Model model) {
        List<OrgLevelMapping> mappings = orgLevelMappingService.findAllMaps(); // Fetch mappings with detailed information
        model.addAttribute("mappings", mappings);
        return "orgLevelMapping/list";
    }

    @GetMapping("/new")
    public String newMapping(Model model) {
        model.addAttribute("orgLevelMapping", new OrgLevelMapping());

        List<OrgLevelDefDTO> orgLevels = orgLevelService.getActiveOrgLevels();

        for (OrgLevelDefDTO orgLevel : orgLevels) {
            // Fetch available entries based on OrgLevelDefId
            List<OrgLevelEntryDTO> availableEntries = orgLevelDao.getAvailableEntries(orgLevel.getOrgLevelDefId());
            orgLevel.setAvailableEntries(availableEntries);

            // Fetch selected entries based on OrgLevelDefId or some mapping
           
            List<OrgLevelEntryDTO> selectedEntries = orgLevelDao.getSelectedEntries(orgLevel.getOrgLevelDefId());
            orgLevel.setSelectedEntries(new ArrayList<>());
          //  orgLevel.setSelectedEntries(selectedEntries);
            //orgLevel.setSelectedEntries(selectedEntries != null ? selectedEntries : new ArrayList<>());
        }

        model.addAttribute("orgLevels", orgLevels);
        return "orgLevelMapping/new";
    }

	/*
	 * @PostMapping("/saveOrgLevelEntries") public ResponseEntity<?>
	 * saveOrgLevelEntries(@RequestBody List<Map<String, Object>> entries) {
	 * System.out.println("Received Data: " + entries);
	 * orgLevelMappingService.saveOrgLevelEntries(entries); return
	 * ResponseEntity.ok("Data saved successfully!"); }
	 */
    @PostMapping("/saveOrgLevelEntries")
    public ResponseEntity<String> saveOrgLevelEntries(@RequestBody List<OrgLevelMapping> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return ResponseEntity.badRequest().body("No data received.");
        }

        // Extract a single name and description (assuming they are the same for all)
        String name = dataList.get(0).getShortName();
        String description = dataList.get(0).getLongDescription();

        // Step 1: Save OrgAcctSet entry (only once)
        OrgLevelMapping orgAcctSet = new OrgLevelMapping();
        // Do not set OrgAcctSetId manually, let SQL Server handle it
        orgAcctSet.setShortName(name);
        orgAcctSet.setLongDescription(description);

        // Save the OrgAcctSet entry and fetch the generated OrgAcctSetId
//        orgLevelMappingService.saveOrgAcctSet(orgAcctSet);
//        int newOrgAcctSetId = orgAcctSet.getOrgAcctSetId(); // Retrieve the generated ID after saving
        int newOrgAcctSetId = orgLevelMappingService.saveOrgAcctSet(orgAcctSet);

        // Step 2: Collect unique selectedEntryIds across all objects
        Set<String> uniqueEntryIds = new HashSet<>();
        for (OrgLevelMapping data : dataList) {
            if (data.getSelectedEntryIds() != null) {
                uniqueEntryIds.addAll(data.getSelectedEntryIds());
            }
        }

        // Step 3: Save only unique mappings
        for (String entryId : uniqueEntryIds) {
            OrgLevelMapping orgLevelMapping = new OrgLevelMapping();
            orgLevelMapping.setOrgAcctSetId(newOrgAcctSetId); // Use the generated OrgAcctSetId
            orgLevelMapping.setOrgLevelEntryId(Integer.parseInt(entryId));
            orgLevelMapping.setUpdatedTm(new java.util.Date());

            orgLevelMappingService.saveOrgLevelMapping(orgLevelMapping);
        }

        return ResponseEntity.ok("Data saved successfully!");
    }


    
    @PostMapping("/save")
    public String saveMapping(@ModelAttribute OrgLevelMapping mapping) {
        orgLevelMappingService.save(mapping);
        return "redirect:/org-level-mapping/list";
    }

	/*
	 * @GetMapping("/edit/{id}") public String editMapping(@PathVariable("id") int
	 * id, Model model) { OrgLevelMapping mapping =
	 * orgLevelMappingService.getMappingById(id);
	 * model.addAttribute("orgLevelMapping", mapping); return
	 * "orgLevelMapping/edit"; }
	 */
    
    @GetMapping("/edit")
    public String editOrgMapping(@RequestParam("id") Long id, Model model) {
        System.out.println("🔍 Received edit request for ID: " + id);
    	 System.out.println("Received request to edit OrgLevelMapping with ID: " + id);
    //    OrgLevelMapping selectedMapping = orgLevelMappingService.getMappingById(id);
    	 OrgLevelMapping basicInfo = orgLevelMappingService.findBasicInfo(id); 
    	  System.out.println("Basic Info: " + basicInfo);
        List<OrgLevelMapping> availableMappings = orgLevelMappingService.findAvailableMappings(id);
        List<OrgLevelMapping> selectedMappings = orgLevelMappingService.findSelectedMappings(id);
        for (OrgLevelMapping mapping : selectedMappings) {
            System.out.println("Mapping Data: EntryId = " + mapping.getOrgLevelEntryId() + ", DefId = " + mapping.getOrgAcctSetId());
        }
        availableMappings.forEach(mapping -> 
        System.out.println("Available Mapping: " + mapping)
    );
    
    selectedMappings.forEach(mapping -> 
        System.out.println("Selected Mapping: " + mapping)
    );
       // List<OrgLevelDef> orgLevelDefs = orgLevelMappingService.findOrgLevelDefinitions(); // Fetch dynamic tabs
        List<OrgLevelDefDTO> orgLevels = orgLevelService.getActiveOrgLevels();
      //  model.addAttribute("selectedMapping", selectedMapping);
        model.addAttribute("basicInfo", basicInfo);
        model.addAttribute("availableMappings", availableMappings);
        model.addAttribute("selectedMappings", selectedMappings);
        model.addAttribute("orgLevelDefs", orgLevels); 
        return "orgLevelMapping/edit";  // Returns the JSP page
    }
   

    
  

}

