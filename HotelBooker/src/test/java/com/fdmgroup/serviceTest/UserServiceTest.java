package com.fdmgroup.serviceTest;

import static org.junit.Assert.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.BeforeClass;
import org.junit.Test;
import com.fdmgroup.entity.User;
import com.fdmgroup.service.UserService;

public class UserServiceTest {
	
	private static UserService userService;
	private static int userId = 0;
	
	@BeforeClass
	public static void setupClass(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hb_persistence_unit");
		userService = new UserService(emf);
	}

	@Test
	public void persistUserInsertsNewUserIntoDatabaseTest() {
		// Create user
		userId++;
		User testUser = createUser(userId);
		String testUserEmail = testUser.getEmail();
		userService.persistUser(testUser);
		
		// Confirm user was persisted in DB
		User retrievedUser = userService.findUser(userId);
		String retrievedUserEmail = retrievedUser.getEmail();
		assertEquals(testUserEmail, retrievedUserEmail);
	}

	@Test
	public void updateUserAltersTheDetailsOfAnExistingUserTest() {
		// Create user
		userId++;
		User testUser = createUser(userId);
		userService.persistUser(testUser);
		
		// Update user
		String newEmail = "harry.alexander.smith@test.com";
		testUser.setEmail(newEmail);
		userService.updateUser(testUser);
		
		// Confirm user was updated on DB
		User retrievedUser = userService.findUser(userId);
		assertEquals("Email was incorrect", newEmail, retrievedUser.getEmail());
	}
	
	@Test
	public void removeUserDeletesUserFromDatabaseTest() {
		// Create user
		userId++;
		User testUser = createUser(userId);
		userService.persistUser(testUser);
		
		// Confirm user was created successfully
		User retrievedUser = userService.findUser(userId);
		assertTrue("User was not created correctly", retrievedUser != null);
		
		// Delete user
		userService.removeUser(userId);
		
		// Confirm user was deleted
		User deletedUser = userService.findUser(userId);
		assertEquals("User was not deleted", null, deletedUser);
	}
	
	/**
	 * Creates a sample User instance
	 * @param id
	 * @return a new instance of User with pre-defined column values  
	 */
	private User createUser(int id) {
		User user = new User();
		user.setId(id);
		user.setFirstname("Harry");
		user.setLastname("Smith");
		user.setEmail("harry.smith@test.com");
		user.setBillingStreet("123 Test Street");
		user.setBillingPostCode("TE570I");
		user.setBillingCountry("UK");
		return user;
	}

}
