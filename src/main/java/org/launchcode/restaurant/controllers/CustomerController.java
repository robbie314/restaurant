package org.launchcode.restaurant.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.restaurant.models.Customer;
import org.launchcode.restaurant.models.MenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerController extends AbstractController {

	@RequestMapping(value = "/createcustomer", method = RequestMethod.GET)
	public String signupForm() {
		return "createcustomer";
	}
	
	@RequestMapping(value = "/customerlookup", method = RequestMethod.GET)
	public String lookUpForm() {
	//	HttpSession thisSession = request.getSession();
		return "customerlookup";
	}
	
	
	@RequestMapping(value = "/customerlookup", method = RequestMethod.POST)
	public String lookUp(HttpServletRequest request, Model model) {
		String phoneNumber=request.getParameter("phoneNumber");
		if (!Customer.isValidPhoneNumber(phoneNumber)) {
			model.addAttribute("phoneNumber_error", "Phone number is not valid.");
			return "customerlookup";
		}
		
		Customer c= customerDao.findByPhoneNumber(phoneNumber);
		if (c == null) {
			
			model.addAttribute("phoneNumber_error", "That phone number doesn't exist! You may create a new customer by clicking the link below");
			
			return "customerlookup";
		} else{
			
			return "redirect:/customerinformation/"+c.getUid();
		}
	}
	
	
	@RequestMapping(value = "/createcustomer", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		// TODO - implement 
		//get parameters from request
		String phoneNumber=request.getParameter("phoneNumber");
		String verifyPhoneNumber=request.getParameter("verifyPhoneNumber");
		String firstName = request.getParameter("firstName");
		String lastName= request.getParameter("lastName");
		String email = request.getParameter("email");

		
		
		//validate parameters (firstname, lastname, phonenumber, verify)
		if (!phoneNumber.equals(verifyPhoneNumber)) {
			model.addAttribute("verify_error", "Phone numbers do not match.");
			model.addAttribute("firstName", firstName);
			model.addAttribute("lastName", lastName);
			model.addAttribute("email", email);
			return "createcustomer";
		} else {
			
			if (!Customer.isValidPhoneNumber(phoneNumber)) {
				model.addAttribute("phoneNumber_error", "Phone Number is not valid. Valid phone number is formatted as 1234567890");
				model.addAttribute("firstName", firstName);
				model.addAttribute("lastName", lastName);
				model.addAttribute("email", email);
				return "createcustomer";
				
			} else if (!Customer.isValidEmail(email)){
				model.addAttribute("email_error", "Email is not valid.");
				model.addAttribute("firstName", firstName);
				model.addAttribute("lastName", lastName);
				model.addAttribute("phoneNumber", phoneNumber);
				model.addAttribute("verifyPhoneNumer", verifyPhoneNumber);
				return "createcustomer";
				
			}
		}
		
		//if they validate, create a new customer, and put them in the session
		Customer c= customerDao.findByPhoneNumber(phoneNumber);
		if (c == null) {
			Customer newUser= new Customer(firstName, lastName, email, phoneNumber);
			customerDao.save(newUser);
			
			
			return "redirect:/customerinformation";
		} else{
			model.addAttribute("phoneNumber_error", "That phone number already exists!");
			model.addAttribute("firstName", firstName);
			model.addAttribute("lastName", lastName);
			model.addAttribute("email", email);
			return "createcustomer";
		}
		
	}
	@RequestMapping(value = "/customerinformation/{uid}", method = RequestMethod.GET)
	public String customerInformation(@PathVariable int uid, Model model) {
		Customer c = customerDao.findByUid(uid);
		if (c == null) {
			model.addAttribute("error", "There is no customer with that id " + uid);
			return "404";
		}
			model.addAttribute("customer", c);
		return "customerinformation";
	}
	
}

