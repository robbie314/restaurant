package org.launchcode.blogz.models;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class User extends Entity {

	private String username;
	private String pwHash;
	private static final List<User> allUsers = new ArrayList<User>();
	
	public User(String username, String password) {
		
		super();
		
		if (!isValidUsername(username)) {
			throw new IllegalArgumentException("Invalid username");
		}
		
		this.username = username;
		this.pwHash = hashPassword(password);
		
		allUsers.add(this);
	}
	
	public String getPwHash() {
		return pwHash;
	}
	
	private static String hashPassword(String password){
		return hashPassword(password, BCrypt.gensalt());
	}
	
	private static String hashPassword(String password, String salt) {
		return BCrypt.hashpw(password, salt) + "," + salt;
	}
	
	public boolean isValidPassword(String password) {
		String salt = this.getPwHash().split(",")[1];
		return this.pwHash.equals(hashPassword(password, salt));
	}
	
	public String getUsername() {
		return username;
	}
	
	public static List<User> getAllUsers() {
		return allUsers;
	}
	
	public static boolean isValidUsername(String username) {
		
		// check for uniqueness
		for (User u : allUsers) {
			if (username.equals(u.getUsername())) {
				return false;
			}
		}
		
		Pattern validUsernamePattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9_-]{4,11}");
		Matcher matcher = validUsernamePattern.matcher(username);
		return matcher.matches();
	}
	
}
