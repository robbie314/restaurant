package org.launchcode.blogz.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		// get submitted parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		HttpSession session = request.getSession();
		
		User existingUser = userDao.findByUsername(username);
		
		if (existingUser != null) {
			model.addAttribute("username_error", "A user with that username already exists. Please choose another username");
			return "signup";
		}
		
		if (!User.isValidUsername(username)) {
			model.addAttribute("username_error", "Usernames must be 5-12 characters long, start with a letter, and contain only letters, numbers, _, or -");
			return "signup";
		}
		
		if (!User.isValidPassword(password)) {
			model.addAttribute("password_error", "Passwords must be 6-20 characters long and may not contain spaces");
			return "signup";
		}
		
		if (!password.equals(verify)) {
			model.addAttribute("username", username);
			model.addAttribute("verify_error", "Passwords do not match");
			return "signup";
		}		
		
		User newUser = new User(username, password);
        userDao.save(newUser);
        setUserInSession(session, newUser);
		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = userDao.findByUsername(username);
		
		// user not found in db
		if (user == null) {
			model.addAttribute("error", "There is no user with username: <b>" + username + "</b>");
			return "login";
		}
		
		// incorrect password
		if (!user.isMatchingPassword(password)) {
			model.addAttribute("error", "Invalid password");
			return "login";
		}
		
		// valid user; log them in!
		setUserInSession(request.getSession(), user);
		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
