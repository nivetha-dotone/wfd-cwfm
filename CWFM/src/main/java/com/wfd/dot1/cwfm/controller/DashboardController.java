package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wfd.dot1.cwfm.dto.CardDto;
import com.wfd.dot1.cwfm.enums.UserRole;
import com.wfd.dot1.cwfm.pojo.MasterUser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	
	@GetMapping("/load")
    public String createGatePass(HttpServletRequest request,HttpServletResponse response) {
	
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
        String role = user.getRoleName().toUpperCase();
        String result=null;
        if (role.contains(UserRole.CONTRACTORSUPERVISOR.getName())) {
        	
            result = "dashboard/csDashboard";
        }else if(role.contains(UserRole.HR.getName())) {
        	
        	result = "dashboard/hrDashboard";
        	
        }else if(role.contains(UserRole.EIC.getName())) {
        	result = "dashboard/eicDashboard";
        }else if(role.contains(UserRole.SECURITY.getName())) {
        	result = "dashboard/secDashboard";
        }else {
        	result = "dashboard/generalDashboard";
        }
        return result;
	}
	
	
	@GetMapping("/view")
	public String loadDashboard(HttpServletRequest request, HttpServletResponse response, Model model) {
	    HttpSession session = request.getSession(false);
	    MasterUser user = (session != null) ? (MasterUser) session.getAttribute("loginuser") : null;

	    String role = user.getRoleName().toUpperCase();
	    List<CardDto> cards = new ArrayList<>();

	    if (role.contains(UserRole.CONTRACTORSUPERVISOR.getName())) {
	        cards.add(new CardDto("Onboarding ", "Manage new workers", "12", "border-onboarding", "icon-onboarding", "resources/img/onboarding.png", "onboarding.jsp"));
	        cards.add(new CardDto("Renewals", "Track contract renewals", "5", "border-renewals", "icon-renewals", "resources/img/verification.png", "renewals.jsp"));
	        cards.add(new CardDto("Verifications", "ID and background checks", "9", "border-verifications", "icon-verifications", "resources/img/official-document.png", "verifications.jsp"));
	        cards.add(new CardDto("Onboarding ", "Manage new workers", "12", "border-onboarding", "icon-onboarding", "resources/img/onboarding.png", "onboarding.jsp"));
	        cards.add(new CardDto("Renewals", "Track contract renewals", "5", "border-renewals", "icon-renewals", "resources/img/verification.png", "renewals.jsp"));
	      
	        cards.add(new CardDto("ESMES", "Manage ESMES data", "3", "border-esmes", "icon-esmes", "resources/img/compliance.png", "esmes.jsp"));
	    }

	    // Add more conditions for HR, EIC, SECURITY, etc. if needed

	    model.addAttribute("cards", cards);
	    return "dashboard/csDashboard"; // keep using the specific dashboard JSP
	}


}
