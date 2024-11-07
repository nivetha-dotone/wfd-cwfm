package com.wfd.dot1.cwfm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/billVerification")
public class BillVerificationController {
	@GetMapping("/list")
    public String billVerification(HttpServletRequest request,HttpServletResponse response) {
    	 
    	return "bill/billverification";
    }
}
