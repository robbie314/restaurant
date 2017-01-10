package org.launchcode.restaurant.controllers;

import java.util.Collections;
import java.util.List;

import org.launchcode.restaurant.models.Post;
import org.launchcode.restaurant.models.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmployeeController extends AbstractController {

	@RequestMapping(value = "/")
	public String index(Model model){
		
		// TODO - fetch users and pass to template
		List<Employee> employees =employeeDao.findAll();
		model.addAttribute("employees", employees);
		return "index";
	}
	
	@RequestMapping(value = "/blog")
	public String blogIndex(Model model) {
		
		// TODO - fetch posts and pass to template
		List<Post> posts = postDao.findAll();
		Collections.reverse(posts);
		model.addAttribute("posts", posts);
		
		return "blog";
	}
	
}
