package com.wfd.dot1.cwfm.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

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
        List<OrgLevelMapping> mappings = orgLevelMappingService.listMappings(); // Fetch mappings with detailed information
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
            orgLevel.setSelectedEntries(selectedEntries);
        }

        model.addAttribute("orgLevels", orgLevels);
        return "orgLevelMapping/new";
    }

    @PostMapping("/saveOrgLevelEntries")
    public ResponseEntity<?> saveOrgLevelEntries(@RequestBody List<Map<String, Object>> entries) {
        System.out.println("Received Data: " + entries);
        orgLevelMappingService.saveOrgLevelEntries(entries);
        return ResponseEntity.ok("Data saved successfully!");
    }
    
    @PostMapping("/save")
    public String saveMapping(@ModelAttribute OrgLevelMapping mapping) {
        orgLevelMappingService.save(mapping);
        return "redirect:/org-level-mapping/list";
    }

    @GetMapping("/edit/{id}")
    public String editMapping(@PathVariable("id") int id, Model model) {
        OrgLevelMapping mapping = orgLevelMappingService.getMappingById(id);
        model.addAttribute("orgLevelMapping", mapping);
        return "orgLevelMapping/edit";
    }
}

