package org.launchcode.restaurant.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.restaurant.models.MenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MenuController extends AbstractController {

	@RequestMapping(value = "/addmenuitem", method = RequestMethod.GET)
	public String signupForm() {
		return "addmenuitem";
	}
	
	
	@RequestMapping(value = "/addmenuitem", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		// TODO - implement signup
		//get parameters from request
		String name=request.getParameter("name");
		String description=request.getParameter("description");
		String price = request.getParameter("price");
				
		
		//validate parameters (firstname, lastname, phonenumber, verify)
		if (!MenuItem.isValidPrice(price)) {
			model.addAttribute("price_error", "Price format is incorrect. Valid price must a be a decimal number.");
			model.addAttribute("name", name);
			model.addAttribute("description", description);
			return "addmenuitem";
		} 
		//if they validate, create a new user, and put them in the session
		MenuItem mi = menuItemDao.findByName(name);
		if (mi == null) {
			MenuItem newMenuItem= new MenuItem(name, description, price);
			menuItemDao.save(newMenuItem);
			
			
			return "redirect:/menu";
		} else{
			model.addAttribute("name_error", "That menu item already exists!");
			model.addAttribute("name", name);
			model.addAttribute("description", description);
			model.addAttribute("price", price);
			return "addmenuitem";
		}
		
	}
	@RequestMapping(value = "/menu", method=RequestMethod.GET)
	public String menu(Model model) {
		List<MenuItem> menuitems = menuItemDao.findAll();
		model.addAttribute("menuitems", menuitems);
		return "menu";
	}
}
