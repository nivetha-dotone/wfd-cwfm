package com.wfd.dot1.cwfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wfd.dot1.cwfm.dto.UserDTO;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.UserService;

@Controller
@RequestMapping("/usersController")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommonService commonService;
    @GetMapping("/new")
    public String createUserForm(Model model) {
    	 List<CmsGeneralMaster> roles = commonService.getAllRoles();
    	 model.addAttribute("roles", roles);
        model.addAttribute("userDTO", new UserDTO());
        return "users/add";
    }

   

    @GetMapping("/userList")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        List<CmsGeneralMaster> roles = commonService.getAllRoles();
   	 model.addAttribute("roles", roles);
        return "users/list";
    }
    @RequestMapping(value = "/saveUsers", method = RequestMethod.POST)
    public String saveUser( MasterUser user,
                           @RequestParam List<Long> roleIds,
                           Model model) {
        try {
            // Delegate saving user to UserService
            userService.saveUser(user, roleIds);

            // Fetch roles to display in the view
            List<CmsGeneralMaster> roles = commonService.getAllRoles();
            model.addAttribute("roles", roles);
            model.addAttribute("successMessage", "User saved successfully!");

            return "redirect:/users/list"; // Redirect to success page or view
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error saving user: " + e.getMessage());
            return "user/error"; // Redirect to error page or view
        }
    }

//    @PostMapping("/saveUsers")
//    public String saveUser(@ModelAttribute MasterUser user,
//                           @RequestParam List<Long> roleIds,
//                           Model model) {
//        try {
//            // Delegate saving user to UserService
//            userService.saveUser(user, roleIds);
//
//            // Fetch roles to display in the view
//            List<CmsGeneralMaster> roles = commonService.getAllRoles();
//            model.addAttribute("roles", roles);
//            model.addAttribute("successMessage", "User saved successfully!");
//
//            return "redirect:/users/list"; // Redirect to success page or view
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "Error saving user: " + e.getMessage());
//            return "user/error"; // Redirect to error page or view
//        }
//    }

}
