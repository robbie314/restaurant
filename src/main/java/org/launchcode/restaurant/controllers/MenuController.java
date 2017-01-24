package org.launchcode.restaurant.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.restaurant.models.MenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
		// get parameters from request
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String price = request.getParameter("price");

		// validate parameters (firstname, lastname, phonenumber, verify)
		if (!MenuItem.isValidPrice(price)) {
			model.addAttribute("price_error", "Price format is incorrect. Valid price must a be a decimal number.");
			model.addAttribute("name", name);
			model.addAttribute("description", description);
			return "addmenuitem";
		}
		//
		MenuItem mi = menuItemDao.findByName(name);
		if (mi == null) {
			MenuItem newMenuItem = new MenuItem(name, description, price);
			menuItemDao.save(newMenuItem);

			return "redirect:/menu";
		} else {
			model.addAttribute("name_error", "That menu item already exists!");
			model.addAttribute("name", name);
			model.addAttribute("description", description);
			model.addAttribute("price", price);
			return "addmenuitem";
		}

	}

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menu(Model model) {
		List<MenuItem> menuitems = menuItemDao.findAll();
		model.addAttribute("menuitems", menuitems);
		return "menu";
	}

	@RequestMapping(value = "/updatemenuitem/{uid}", method = RequestMethod.GET)
	public String menuItem(@PathVariable int uid, Model model) {

		MenuItem m = menuItemDao.findByUid(uid);
		if (m == null) {

			model.addAttribute("error", "There is no menu item with that id " + uid);
			return "404";
		}

		model.addAttribute("menuitem", m);
		return "updatemenuitem";
	}

	@RequestMapping(value = "/updatemenuitem/{uid}", method = RequestMethod.POST)
	public String updateMenuItem(HttpServletRequest request, @PathVariable int uid, Model model) {

		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String price = request.getParameter("price");
		String status = request.getParameter("status");

		// validate parameters (price, name)
		if (!MenuItem.isValidPrice(price)) {
			model.addAttribute("price_error", "Price format is incorrect. Valid price must a be a decimal number.");
			model.addAttribute("name", name);
			model.addAttribute("description", description);
			return "addmenuitem";
		}

		MenuItem tempItem = new MenuItem(name, description, price, status);
		MenuItem currentItem = menuItemDao.findByUid(uid);
		if (!tempItem.getName().equals(currentItem.getName())) {

			
			MenuItem mi = menuItemDao.findByName(name);
			if (mi == null) {
				currentItem.setName(name);
				currentItem.setDescription(description);
				currentItem.setPrice(price);
				currentItem.setStatus(status);
				menuItemDao.save(currentItem);

				return "redirect:/menu";
			} else {
				model.addAttribute("name_error", "That menu item name already exists!");
				tempItem.setName("");
				model.addAttribute("menuitem", tempItem);

				return "updatemenuitem";
			}
		}	else {
			currentItem.setDescription(description);
			currentItem.setPrice(price);
			currentItem.setStatus(status);
			menuItemDao.save(currentItem);

			return "redirect:/menu";
		}
	}
}
