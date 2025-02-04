package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;

@Controller
@RequestMapping("/principalEmployer")
public class PrincipalEmployerController {
	
	@Autowired
	PrincipalEmployerService peService;
	@GetMapping("/list")
    public String getAllPrincipalEmployer(HttpServletRequest request,HttpServletResponse response) {
    	 HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
         List<PrincipalEmployer> listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
    		request.setAttribute("cmSPRINCIPALEMPLOYERs", listDto);
    	
    	 
    	return "principalEmployer/list";
    }
	
	@GetMapping("/view/{id}")
    public String getIndividualPEDetailByUnitId(@PathVariable("id") String id,HttpServletRequest request,HttpServletResponse response) {
		PrincipalEmployer principalEmployer = peService.getIndividualPEDetailByUnitId(id);
        request.setAttribute("principalEmployer", principalEmployer);
        return "principalEmployer/view";
    }
	
	

}
