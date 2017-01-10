package org.launchcode.restaurant.controllers;

import javax.servlet.http.HttpSession;

import org.launchcode.restaurant.models.Employee;
import org.launchcode.restaurant.models.dao.PostDao;
import org.launchcode.restaurant.models.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

	@Autowired
    protected EmployeeDao employeeDao;
	
	@Autowired
	protected PostDao postDao;

    public static final String userSessionKey = "user_id";

    protected Employee getUserFromSession(HttpSession session) {
    	
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId == null ? null : employeeDao.findByUid(userId);
    }
    
    protected void setUserInSession(HttpSession session, Employee user) {
    	session.setAttribute(userSessionKey, user.getUid());
    }
	
}
