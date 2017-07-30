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
		userId++;
		User testUser = createUser(userId);
		String testUserEmail = testUser.getEmail();
		userService.persistUser(testUser);
		
		User retrievedUser = userService.findUser(userId);
		String retrievedUserEmail = retrievedUser.getEmail();
		assertEquals(testUserEmail, retrievedUserEmail);
	}

	@Test
	public void updateUserAltersTheDetailsOfAnExistingUserTest() {
		userId++;
		User testUser = createUser(userId);
		userService.persistUser(testUser);
		
		String newEmail = "harry.alexander.smith@test.com";
		testUser.setEmail(newEmail);
		userService.updateUser(testUser);
		
		User retrievedUser = userService.findUser(userId);
		assertEquals(newEmail, retrievedUser.getEmail());
	}
	
	@Test
	public void removeUserDeletesUserFromDatabaseTest() {
		userId++;
		User testUser = createUser(userId);
		userService.persistUser(testUser);
		
		// Check User was created successfully in the first place
		User retrievedUser = userService.findUser(userId);
		assert(retrievedUser != null);
		
		userService.removeUser(userId);
		User deletedUser = userService.findUser(userId);
		assert(deletedUser == null);
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
