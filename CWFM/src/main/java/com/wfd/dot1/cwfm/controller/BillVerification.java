package com.wfd.dot1.cwfm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wfd.dot1.cwfm.entity.CMSContractor;

@Controller
@RequestMapping("/bvr")
public class BillVerification {

	 @GetMapping("/add")
	    public String addPrincipalEmployerForm(HttpServletRequest request,Model model) {
	        model.addAttribute("contractor", new CMSContractor());
	        request.setAttribute("LLStatusID", "No");
	        request.setAttribute("WCStatusID", "No");
	        request.setAttribute("PFStatusID", "No");
	        request.setAttribute("ESICStatusID", "No");
	        request.setAttribute("PTaxStatusID", "No");
	        request.setAttribute("MLWFStatusID", "No");
	        request.setAttribute("WRPrecMonStatusID", "No");
	        request.setAttribute("AttenRegStatusID", "No");
	        request.setAttribute("PFChallanStatusID", "No");
	        
	        request.setAttribute("WagePrecMonStatusID", "No");
	        request.setAttribute("MinWageStatusID", "No");
	        request.setAttribute("BankStatementStatusID","No");
	        request.setAttribute("DiscrepancyStatusID","No");
	        return "billVerification/add";
	    }

}
