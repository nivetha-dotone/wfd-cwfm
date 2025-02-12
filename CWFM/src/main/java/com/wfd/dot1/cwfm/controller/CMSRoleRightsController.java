package com.wfd.dot1.cwfm.controller;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wfd.dot1.cwfm.dto.RoleRightsForm;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.service.CommonService;

@Controller
@RequestMapping("/roleRights")
public class CMSRoleRightsController {

	
    @Autowired
    private CommonService commonService;

    @GetMapping("/roleRights")
    public ModelAndView showRoleRights() {
        List<CMSRoleRights> roleRightsList = commonService.getAllRoleRights();
        ModelAndView modelAndView = new ModelAndView("roleRightsList");
        modelAndView.addObject("roleRightsList", roleRightsList);
        List<CmsGeneralMaster> pages = commonService.getAllPages();
        List<CmsGeneralMaster> roles = commonService.getAllRoles();
        ((Model) modelAndView).addAttribute("roles", roles);
        ((Model) modelAndView).addAttribute("pages", pages);
        return modelAndView;
    }
    
    @RequestMapping("/roleRightsList")
    public String showRoleRights(Model model) {
        List<CMSRoleRights> roleRightsList = commonService.getAllRoleRights();
        model.addAttribute("roleRightsList", roleRightsList);
        List<CmsGeneralMaster> pages = commonService.getAllPages();
        List<CmsGeneralMaster> roles = commonService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("pages", pages);
        return "roleRights/list";
    }
    

    @GetMapping("/add")
    public String showAddRoleRightForm(Model model) {
    	List<CmsGeneralMaster> pages = commonService.getAllPages();
        List<CmsGeneralMaster> roles = commonService.getAllRoles();
        RoleRightsForm roleRightsForm = new RoleRightsForm();
        roleRightsForm.setRoleRights(Collections.singletonList(new CMSRoleRights()));

        model.addAttribute("roles", roles);
        model.addAttribute("pages", pages);
        model.addAttribute("roleRightsForm", roleRightsForm);
        return "roleRights/add"; // This should map to addRoleRight.jsp
    }
//    @GetMapping("/getPagesByRole/{roleId}")
//    @ResponseBody
//    public List<CmsGeneralMaster> getPagesByRole(@PathVariable Long roleId) {
//        System.out.println("roleId---" + roleId);
//        List<CmsGeneralMaster> pages = commonService.getPagesByRole(roleId);
//        System.out.println("Pages returned: " + pages); // Check if this prints the correct data
//        return pages;
//    }
    
    @GetMapping("/getPagesByRole")
    @ResponseBody
    public List<CmsGeneralMaster> getPagesByRole(@RequestParam(required = false) Long roleId) {
        System.out.println("roleId---" + roleId);
        return commonService.getPagesByRole(roleId); // Fetch pages based on selected role
    }
    
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            MasterUser user = (MasterUser) authentication.getPrincipal();
            return (long) user.getUserId();// Replace with actual logic to retrieve the user ID
        }
        return 1L; // Default user ID for testing or fallback
    }
   
    @RequestMapping(value = "/saveRoleRights", method = RequestMethod.POST)
    public String saveRoleRights(RoleRightsForm roleRights, RedirectAttributes redirectAttributes) {
        try {
            for (CMSRoleRights roleRight : roleRights.getRoleRights()) {
                if (!isDuplicateRoleRight(roleRight)) {
                    processRoleRight(roleRight, getCurrentUserId()); // Additional processing if needed
                    commonService.saveRoleRights(roleRight);
                } else {
                    redirectAttributes.addFlashAttribute("error", "Duplicate Role-Page combination detected for Role: " 
                                                         + roleRight.getRoleId() + " and Page: " + roleRight.getPageId());
                    return "redirect:/roleRights/roleRightsList";
                }
            }
            redirectAttributes.addFlashAttribute("success", "Role rights saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to save role rights. Error: " + e.getMessage());
        }

        // Redirect back to the role rights list
        return "redirect:/roleRights/roleRightsList";
    }





    private boolean isDuplicateRoleRight(CMSRoleRights roleRight) {
        return commonService.checkDuplicateRoleRight(roleRight.getRoleId(), roleRight.getPageId());
    }


	/*
	 * @PostMapping("/saveRoleRights") public ModelAndView
	 * saveRoleRights(@ModelAttribute RoleRightsForm roleRightsForm) {
	 * roleRightsForm.getRoleRights().forEach(roleRight -> {
	 * processRoleRight(roleRight, getCurrentUserId());
	 * commonService.saveRoleRights(roleRight); }); ModelAndView mav = new
	 * ModelAndView("roleRights/list"); mav.addObject("roleRights",
	 * commonService.getAllRoleRights()); return mav; }
	 */
//    @PostMapping("/saveRoleRights")
//    public ResponseEntity<String> saveRoleRights(@ModelAttribute RoleRightsForm roleRightsForm) {
//        Long currentUserId = getCurrentUserId();
//        roleRightsForm.getRoleRights().forEach(roleRight -> {
//            processRoleRight(roleRight, currentUserId);
//            commonService.saveRoleRights(roleRight);
//        });
//
//        return ResponseEntity.ok("Role rights saved successfully.");
//    }
//    @PostMapping("/saveRoleRights")
//    public ResponseEntity<String> saveRoleRights(@RequestBody RoleRightsForm roleRightsForm) {
////        Long currentUserId = 1L;
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////      if (authentication != null && authentication.isAuthenticated()) {
////          MasterUser user = (MasterUser) authentication.getPrincipal();
////          currentUserId = (long) 1;
////      }
//    	  Long currentUserId = getCurrentUserId();
//        roleRightsForm.getRoleRights().forEach(roleRight -> {
//            processRoleRight(roleRight, currentUserId);
//            commonService.saveRoleRights(roleRight);
//        });
//
//        return ResponseEntity.ok("Role rights saved successfully.");
//    }
    private void processRoleRight(CMSRoleRights roleRight, Long currentUserId) {
        roleRight.setCreatedBy(currentUserId);
        roleRight.setLastUpdatedBy(currentUserId);
        roleRight.setCreationDate(LocalDateTime.now());
        roleRight.setEnabledFlag(1);
        roleRight.setDeletedFlag(0);

        // Set default rights
        roleRight.setAddRights(roleRight.getAddRights() != null ? roleRight.getAddRights() : 1);
        roleRight.setEditRights(roleRight.getEditRights() != null ? roleRight.getEditRights() : 1);
        roleRight.setDeleteRights(roleRight.getDeleteRights() != null ? roleRight.getDeleteRights() : 1);
        roleRight.setViewRights(roleRight.getViewRights() != null ? roleRight.getViewRights() : 1);
        roleRight.setImportRights(roleRight.getImportRights() != null ? roleRight.getImportRights() : 1);
        roleRight.setExportRights(roleRight.getExportRights() != null ? roleRight.getExportRights() : 1);
        roleRight.setListRights(roleRight.getListRights() != null ? roleRight.getListRights() : 0);

        // Fetch page information
        if (roleRight.getPage() != null && roleRight.getPage().getGmId() != null) {
            CmsGeneralMaster page = commonService.findCmsGeneralMasterById(roleRight.getPage().getGmId());
            roleRight.setPage(page);
        }
    }

//    @PostMapping("/saveRoleRights")
//    public ModelAndView saveRoleRights(@ModelAttribute("roleRightsForm") RoleRightsForm roleRightsForm) {
//        List<CMSRoleRights> roleRightsList = roleRightsForm.getRoleRights();
//        Long currentUserId = 1L; // Replace with actual user ID from authentication context
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            MasterUser user = (MasterUser) authentication.getPrincipal();
//            currentUserId = (long) 1;
//        }
//
//        for (CMSRoleRights roleRight : roleRightsList) {
//            roleRight.setCreatedBy(currentUserId);
//            roleRight.setLastUpdatedBy(currentUserId);
//            roleRight.setCreationDate(LocalDateTime.now());
//            roleRight.setEnabledFlag(1); // Default active
//            roleRight.setDeletedFlag(0); // Not deleted
//         // Initialize rights to 0 if they are not explicitly set
//            roleRight.setAddRights(roleRight.getAddRights() != null ? roleRight.getAddRights() : 0);
//            roleRight.setEditRights(roleRight.getEditRights() != null ? roleRight.getEditRights() : 0);
//            roleRight.setDeleteRights(roleRight.getDeleteRights() != null ? roleRight.getDeleteRights() : 0);
//            roleRight.setViewRights(roleRight.getViewRights() != null ? roleRight.getViewRights() : 0);
//            roleRight.setImportRights(roleRight.getImportRights() != null ? roleRight.getImportRights() : 0);
//            roleRight.setExportRights(roleRight.getExportRights() != null ? roleRight.getExportRights() : 0);
//            roleRight.setListRights(roleRight.getListRights() != null ? roleRight.getListRights() : 0);  
//            System.out.println("roleId---" + roleRight.getAddRights());
//            roleRight.setAddRights(roleRight.getAddRights());
//            if (roleRight.getPage() != null && roleRight.getPage().getGmId() != null) {
//                CmsGeneralMaster page = commonService.findCmsGeneralMasterById(roleRight.getPage().getGmId());
//                roleRight.setPage(page);
//            }
//
//            // Save to the database
//            commonService.saveRoleRights(roleRight);
//            System.out.println("roleId---" + roleRight);
//        }
//
//        return new ModelAndView("roleRights/roleRightsList");
//    }

    
    @GetMapping("/edit/{id}")
    public String showEditRoleRightForm(@PathVariable Long id, Model model) {
    	CMSRoleRights roleRightDto = (CMSRoleRights) commonService.getRoleRightsByRoleId(id);
        model.addAttribute("roleRightDto", roleRightDto);
        model.addAttribute("roleList", commonService.getAllRoleRights());
        model.addAttribute("pages", commonService.getAllPages());
        return "editRoleRight"; // This should map to editRoleRight.jsp
    }

    @PostMapping("/update/{id}")
    public String updateRoleRight(@PathVariable Long id, CMSRoleRights roleRightDto, Model model) {
        try {
        	//commonService.updateRoleRights(id, roleRightDto);
            model.addAttribute("successMessage", "Role rights updated successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating role rights: " + e.getMessage());
        }
        return "redirect:/roleRights/list"; // Redirect to the list page
    }

    @GetMapping("/delete/{id}")
    public String deleteRoleRight(@PathVariable Long id, Model model) {
        try {
        	//commonService.deleteRoleRight(id);
            model.addAttribute("successMessage", "Role rights deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error deleting role rights: " + e.getMessage());
        }
        return "redirect:/roleRights/list"; // Redirect to the list page
    }
}
