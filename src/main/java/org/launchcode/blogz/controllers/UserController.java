package org.launchcode.blogz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	
	@RequestMapping(value = "/user/new", method = RequestMethod.GET)
	@ResponseBody
	public String newUserForm() {
		return "new user form";
	}
	
	@RequestMapping(value = "/user/new", method = RequestMethod.POST)
	@ResponseBody
	public String createNewUser() {
		return "new user create";
	}
	
}
