package com.wfd.dot1.cwfm.controller;

import java.util.stream.Stream;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.service.MasterUserService;

@Controller
public class HomeController {
	
	@Autowired
	MasterUserService masterUserService;

	@RequestMapping(path = "/userlogin", method = RequestMethod.POST)
	public String login(@RequestParam("email") String em, @RequestParam("password") String pwd, HttpSession session) {

		MasterUser user = masterUserService.findMasterUserDetailsByUserName(em, pwd);

		
		if (user != null) {
			 String initials = Stream.of(user.getFirstName(), user.getLastName())
		                .map(name -> String.valueOf(name.charAt(0)))  // Get the first character
		                .reduce("", (a, b) -> a + b); 
			session.setAttribute("userInitials", initials);
			session.setAttribute("loginuser", user);
			return "WelcomePage";
		} else {
			session.setAttribute("msg", "invalid email and password");
			return "redirect:/UserLogin.jsp";
		}

	}
	/*
	 * @PostMapping("/changePassword")
	 * 
	 * @ResponseBody public ResponseEntity<String> changePassword(@RequestBody
	 * MasterUser masteruser, HttpSession session) { String currentUserId = (String)
	 * session.getAttribute("userId"); // Fetch the logged-in user's ID from the
	 * session
	 * 
	 * if (currentUserId == null) { return
	 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in."); }
	 * 
	 * if (masterUserService.validateOldPassword(currentUserId,
	 * masteruser.getPassword())) { masterUserService.updatePassword(currentUserId,
	 * masteruser.getNewPassword()); return
	 * ResponseEntity.ok("Password changed successfully."); } else { return
	 * ResponseEntity.status(HttpStatus.BAD_REQUEST).
	 * body("Old password is incorrect."); } }
	 */
}
