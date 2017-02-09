package org.launchcode.restaurant.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.launchcode.restaurant.models.CartItem;
import org.launchcode.restaurant.models.Customer;
import org.launchcode.restaurant.models.MenuItem;
import org.launchcode.restaurant.models.OrderHistory;
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
		double totalPrice = 0;
		for (CartItem ci: cartItems){
			totalPrice += ci.getPrice();
		}
		List<OrderHistory> orderhistory = orderHistoryDao.findByCustomerOrderByOrderDateDesc(c);
		List<OrderHistory> ohQuantity = orderHistoryDao.findByCustomerOrderByQuantityDesc(c);
		List<MenuItem> recommendations = getRecommendations(activeMenuItems, cartItems, ohQuantity);
		model.addAttribute("recommendations", recommendations);
		model.addAttribute("orderhistory", orderhistory);
		model.addAttribute("totalPrice", totalPrice);
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
		double totalPrice = 0;
		for (CartItem cartItem: cartItems){
			totalPrice += cartItem.getPrice();
		}
		List<OrderHistory> orderhistory = orderHistoryDao.findByCustomerOrderByOrderDateDesc(c);
		List<OrderHistory> ohQuantity = orderHistoryDao.findByCustomerOrderByQuantityDesc(c);
		List<MenuItem> recommendations = getRecommendations(activeMenuItems, cartItems, ohQuantity);
		model.addAttribute("recommendations", recommendations);
		model.addAttribute("orderhistory", orderhistory);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartitems", cartItems);
		model.addAttribute("customer", c);
		return "customerinformation";
	}
	@RequestMapping(value = "/removefromcart/{customerUid}/{cartitemUid}", method = RequestMethod.GET)
	public String removeFromCart(@PathVariable int customerUid, @PathVariable int cartitemUid, Model model) {
		Customer c = customerDao.findByUid(customerUid);
		CartItem ci = cartItemDao.findByUid(cartitemUid);
		List<MenuItem> activeMenuItems = menuItemDao.findByStatus("Active");
		model.addAttribute("menuitems", activeMenuItems);
		if (c == null) {
			model.addAttribute("error", "There is no customer with that ID " + customerUid);
			return "404";
		}
		if (ci == null) {
			model.addAttribute("error", "There is no cart item with that ID " + cartitemUid);
			return "404";
		}
		if (ci.getQuantity()==1) {
			cartItemDao.delete(ci);
		}else {
			ci.subtractQuantity();
			cartItemDao.save(ci);
		}
		
		
		List <CartItem> cartItems = cartItemDao.findByCustomer(c);
		double totalPrice = 0;
		for (CartItem cartItem: cartItems){
			totalPrice += cartItem.getPrice();
		}
		List<OrderHistory> orderhistory = orderHistoryDao.findByCustomerOrderByOrderDateDesc(c);
		List<OrderHistory> ohQuantity = orderHistoryDao.findByCustomerOrderByQuantityDesc(c);
		List<MenuItem> recommendations = getRecommendations(activeMenuItems, cartItems, ohQuantity);
		model.addAttribute("recommendations", recommendations);
		model.addAttribute("orderhistory", orderhistory);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartitems", cartItems);
		model.addAttribute("customer", c);
		return "customerinformation";
	}
	@RequestMapping(value = "/submitorder/{customerUid}", method = RequestMethod.GET)
	public String submitOrder(@PathVariable int customerUid, Model model) {
		Customer c = customerDao.findByUid(customerUid);
		
		List<MenuItem> activeMenuItems = menuItemDao.findByStatus("Active");
		model.addAttribute("menuitems", activeMenuItems);
		if (c == null) {
			model.addAttribute("error", "There is no customer with that ID " + customerUid);
			return "404";
		}
		
		
		
		
		List <CartItem> cartItems = cartItemDao.findByCustomer(c);
		if (cartItems == null || cartItems.isEmpty()) {
			model.addAttribute("order_error", "The cart cannot be empty.");
			
		} else {
			for (CartItem ci: cartItems) {
				OrderHistory oh = orderHistoryDao.findByCustomerAndMenuItem(c, ci.getMenuItem());
			
				if (oh == null) {
					oh = new OrderHistory(c, ci.getMenuItem(), ci.getQuantity());
					orderHistoryDao.save(oh);
					cartItemDao.delete(ci);
				} else {
					oh.addQuantity(ci.getQuantity());
					orderHistoryDao.save(oh);
					cartItemDao.delete(ci);
				}
			}
			
		}
		cartItems = cartItemDao.findByCustomer(c);
		List<OrderHistory> orderhistory = orderHistoryDao.findByCustomerOrderByOrderDateDesc(c);
		List<OrderHistory> ohQuantity = orderHistoryDao.findByCustomerOrderByQuantityDesc(c);
		List<MenuItem> recommendations = getRecommendations(activeMenuItems, cartItems, ohQuantity);
		model.addAttribute("recommendations", recommendations);
		model.addAttribute("orderhistory", orderhistory);
		double totalPrice = 0;
		for (CartItem cartItem: cartItems){
			totalPrice += cartItem.getPrice();
		}
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartitems", cartItems);
		model.addAttribute("customer", c);
		return "customerinformation";
	}
	private List <MenuItem> getRecommendations(List <MenuItem> activemi, List <CartItem> ci, List <OrderHistory> oh) {
		List <MenuItem> recommendations = new ArrayList<MenuItem>();
		List <Integer> cartItemIds = new ArrayList<Integer>();
		for (CartItem c: ci) {
			cartItemIds.add(c.getMenuItem().getUid());
		}
		if (oh != null && oh.size()>0) {
		
			while (recommendations.size() <3 && activemi.size()-ci.size() >=3 ) {
				for (OrderHistory history: oh ) {
					if (!cartItemIds.contains(history.getMenuItem().getUid())) {
						recommendations.add(history.getMenuItem());
						if (recommendations.size() >=3 ) {
							
							break; 
						}
					}
				}
				while (recommendations.size() <3 && activemi.size()-ci.size() >= 3 ) {
					addNextRandomItem(activemi, cartItemIds, recommendations);
				}
			}
			
		} else { //have to have at least 3 things to recommend
			while (recommendations.size() <3 && activemi.size()-ci.size() >= 3 ) {
				addNextRandomItem(activemi, cartItemIds, recommendations);
			}
		
		}
		return recommendations;
	}	
	private void addNextRandomItem(List <MenuItem> activemi, List <Integer> ci, List <MenuItem> recommendations) {
		Random rand = new Random();
		int index = rand.nextInt(activemi.size());
		List <Integer> recommendationsIds = new ArrayList<Integer>();
		for (MenuItem mi: recommendations) {
			recommendationsIds.add(mi.getUid());
		}
		while (recommendationsIds.contains(activemi.get(index).getUid()) || ci.contains(activemi.get(index).getUid())) {
			index = rand.nextInt(activemi.size());
		}
		recommendations.add(activemi.get(index));
	}
	
}
