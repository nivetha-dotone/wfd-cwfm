package com.wfd.dot1.cwfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wfd.dot1.cwfm.dto.UserDTO;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.UserService;

@Controller
@RequestMapping("/usersController")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
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
    @PostMapping("/saveUsers")
    public ResponseEntity<?> saveUsers(@RequestBody UserDTO userDTO) {
        try {
            System.out.println("Controller hit: saveUsers");
            System.out.println("Received user: " + userDTO.getUser());
            System.out.println("Received roles: " + userDTO.getRoleIds());

            // Validate input
            if (userDTO.getUser() == null) {
                throw new IllegalArgumentException("User data is required");
            }
            if (userDTO.getRoleIds() == null || userDTO.getRoleIds().isEmpty()) {
                throw new IllegalArgumentException("At least one role ID is required");
            }

            // Save user
            userService.saveUser(userDTO.getUser(), userDTO.getRoleIds());

            return ResponseEntity.ok("User saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error saving user: " + e.getMessage());
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
