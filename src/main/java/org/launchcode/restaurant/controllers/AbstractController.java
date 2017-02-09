package org.launchcode.restaurant.controllers;

import javax.servlet.http.HttpSession;

import org.launchcode.restaurant.models.Employee;
import org.launchcode.restaurant.models.dao.CartItemDao;
import org.launchcode.restaurant.models.dao.CustomerDao;
import org.launchcode.restaurant.models.dao.EmployeeDao;
import org.launchcode.restaurant.models.dao.MenuItemDao;
import org.launchcode.restaurant.models.dao.OrderHistoryDao;
import org.launchcode.restaurant.models.dao.PostDao;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

	@Autowired
    protected EmployeeDao employeeDao;
	
	@Autowired
	protected CustomerDao customerDao;
	
	@Autowired
	protected PostDao postDao;
	
	@Autowired
	protected MenuItemDao menuItemDao;
	
	@Autowired
	protected CartItemDao cartItemDao;
	
	@Autowired
	protected OrderHistoryDao orderHistoryDao;
	

    public static final String userSessionKey = "user_id";

    protected Employee getUserFromSession(HttpSession session) {
    	
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId == null ? null : employeeDao.findByUid(userId);
    }
    
    protected void setUserInSession(HttpSession session, Employee user) {
    	session.setAttribute(userSessionKey, user.getUid());
    }
	
}
