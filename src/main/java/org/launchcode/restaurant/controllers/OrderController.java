package org.launchcode.restaurant.controllers;

import java.util.List;

import org.launchcode.restaurant.models.CartItem;
import org.launchcode.restaurant.models.Customer;
import org.launchcode.restaurant.models.MenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OrderController extends AbstractController {

	@RequestMapping(value = "/customerinformation/{uid}", method = RequestMethod.GET)
	public String customerInformation(@PathVariable int uid, Model model) {
		Customer c = customerDao.findByUid(uid);
		List<MenuItem> activeMenuItems = menuItemDao.findByStatus("Active");
		model.addAttribute("menuitems", activeMenuItems);
		if (c == null) {
			model.addAttribute("error", "There is no customer with that id " + uid);
			return "404";
		}
		List <CartItem> cartItems = cartItemDao.findByCustomer(c);
		model.addAttribute("cartitems", cartItems);
		model.addAttribute("customer", c);
		return "customerinformation";
	}

	@RequestMapping(value = "/addtocart/{customerUid}/{menuitemUid}", method = RequestMethod.GET)
	public String addToCart(@PathVariable int customerUid, @PathVariable int menuitemUid, Model model) {
		Customer c = customerDao.findByUid(customerUid);
		MenuItem mi = menuItemDao.findByUid(menuitemUid);
		List<MenuItem> activeMenuItems = menuItemDao.findByStatus("Active");
		model.addAttribute("menuitems", activeMenuItems);
		if (c == null) {
			model.addAttribute("error", "There is no customer with that ID " + customerUid);
			return "404";
		}
		if (mi == null) {
			model.addAttribute("error", "There is no menu item with that ID " + menuitemUid);
			return "404";
		}
		CartItem ci = cartItemDao.findByCustomerAndMenuItem(c, mi);
		if (ci == null) {
			ci = new CartItem(c, mi);
			
		} else {
			ci.addQuantity();
		}
		cartItemDao.save(ci);
		List <CartItem> cartItems = cartItemDao.findByCustomer(c);
		model.addAttribute("cartitems", cartItems);
		model.addAttribute("customer", c);
		return "customerinformation";
	}
}
