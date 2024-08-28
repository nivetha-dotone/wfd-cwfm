package com.wfd.dot1.cwfm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wfd.dot1.cwfm.entity.UserLogin;
import com.wfd.dot1.cwfm.service.UserLoginService;

@Controller
@RequestMapping("/user")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    // Mapping to display add user form
    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new UserLogin());
        return "/users/add";
    }

    // Mapping to handle form submission for adding a user
    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") UserLogin user) {
        userLoginService.addUser(user);
        return "redirect:/users/list";
    }

    // Mapping to display list of users
    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userLoginService.getAllUsers());
        return "/users/list";
    }
}

