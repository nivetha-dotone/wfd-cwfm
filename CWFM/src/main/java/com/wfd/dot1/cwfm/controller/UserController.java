package com.wfd.dot1.cwfm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wfd.dot1.cwfm.dao.UserDao;
import com.wfd.dot1.cwfm.entity.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDao userDao;

//	@PostMapping("/login")
//	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
//	    User user = userDao.loginUser(email, password);
//	    if (user != null) {
//	        session.setAttribute("loginuser", user);
//	        return "redirect:/user/userlist";
//	    } else {
//	        session.setAttribute("msg", "Invalid email and password");
//	        return "redirect:/UserList.jsp";
//	    }
//	}userListContainer
    
	@RequestMapping("/userlist")
	public String getUserList(Model model) {
	    List<User> userList = userDao.getAllUsers();
	    model.addAttribute("userList", userList);
	    if (userList != null && !userList.isEmpty()) {
	        System.out.println("checkk1 " + userList.get(0).getFullname());
	    } else {
	        System.out.println("User list is empty or null");
	    }
	    return "UserList";
	}
	 @RequestMapping("/logout")
	    public String logout(HttpServletRequest request) {
	        HttpSession session = request.getSession(false); // Don't create a new session if it doesn't exist
	        if (session != null) {
	            session.invalidate(); // Invalidate the session
	            System.out.println("session invalidad");
	        }
	        else {
		        System.out.println("session invalid");
		    }
	        // Get the context path dynamically
	        String contextPath = request.getContextPath();

	        // Redirect to the login page with the context path included
	        return "redirect:/UserLogin.jsp";
	    }

	/*
	 * @GetMapping("/welcome") public String welcome(Model model) { List<User>
	 * userList = userDao.getAllUsers(); if (userList != null &&
	 * !userList.isEmpty()) { System.out.println("checkk2 " +
	 * userList.get(0).getFullname()); } else {
	 * System.out.println("User list is empty or null"); }
	 * model.addAttribute("userList", userList); return "UserList"; }
	 */
//    @RequestMapping(path = "/userlist")
//	public String home(Model m) {
//
//		List<User> list = userDao.getAllUsers();
//		m.addAttribute("userList", list);
//		return "WelcomePage";
//	}
    
//    @RequestMapping(path = "/userlist", method = RequestMethod.POST)
//	public String login(@RequestParam("email") String em, @RequestParam("password") String pwd, HttpSession session) {
//
//		User user = userDao.loginUser(em, pwd);
//
//		if (user != null) {
//			session.setAttribute("loginuser", user);
//			return "WelcomePage";
//		} else {
//			session.setAttribute("msg", "invalid email and password");
//			return "redirect:/UserList.jsp";
//		}
//
//	}
}
