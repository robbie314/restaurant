package org.launchcode.blogz;

import org.junit.Test;
import org.launchcode.blogz.models.User;

import junit.framework.TestCase;

public class PostAndUserTest extends TestCase {

	private static User testUser;
	private static boolean initialized = false;
	private static String testPassword = "asdf";
	private static String testUsername = "chris";
	
	public PostAndUserTest() {
		if (initialized) {
			return;
		}
		
		testUser = new User(testUsername, testPassword);
		initialized = true;
	}
	
	@Test
	@SuppressWarnings("unused")
	public void testUniqueUsername(){
		
		try {
			User u2 = new User(testUsername, testPassword);
			fail("Should not be able to create multiple users with same username");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}
	
	@Test
	public void testEquals(){
		User sameFields = new User("Chris", testPassword);
		assertFalse(sameFields.equals(testUser));
		assertTrue(sameFields.equals(sameFields));
		assertTrue(testUser.equals(testUser));
	}
	
	@Test 
	public void testCreatePost(){}
	
	@Test
	public void testGetPostsByUser(){}
	
	@Test
	public void testIsValidUsername() {
		// TODO - test other restrictions
		assertFalse("Duplicate usernames are not allowed", User.isValidUsername(testUsername));
	}
	
	@Test
	public void testUniqueIds() {}
	
	@Test
	public void testIsValidPassword() {
		assertTrue("A user's correct password should validate", testUser.isValidPassword(testPassword));
		assertFalse("A different password should not validate", testUser.isValidPassword(testPassword + "1"));
	}
	
}
