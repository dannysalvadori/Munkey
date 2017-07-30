package com.fdmgroup.serviceTest;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.entity.Trainee;
import com.fdmgroup.service.TraineeService;

public class TraineeServiceTest {

	private static TraineeService traineeService;
	private Trainee trainee1;
	
	@BeforeClass
	public static void setupClass(){
		
		//Set up logging
//		BasicConfigurator.configure();
		
		/*Create an EntityManagerFactory and inject it into TraineeService
		  Note EntityManagerFactory objects are computationally expensive to create and
		  can be re-used, so only need to be created once. They can be created as a bean in Spring.
		  You must request the Factory with the same name as that defined in persistence.xml*/
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hb_persistence_unit");
		
		traineeService = new TraineeService(emf);
	}
	
	@Before
	public void setup(){
		trainee1 = new Trainee();
		trainee1.setTraineeId(0);
		trainee1.setUsername("john.smith");
		trainee1.setFirstname("John");
		trainee1.setLastname("Smith");
	}
	
	@Test
	public void testTraineeServicePersistTraineeMethodWritesAPassedTraineeToTheDatabase() {
		traineeService.persistTrainee(trainee1);
		Trainee retrievedTrainee = traineeService.findTrainee(0);
		assertEquals(trainee1.getUsername(), retrievedTrainee.getUsername());
	}
	
	@Test
	public void testTraineeServiceUpdateTraineeAltersTheUserDetailsOfAnExistingTrainee(){
		Trainee trainee3 = new Trainee();
		trainee3.setFirstname("Jean-Luc");
		trainee3.setLastname("Picard");
		trainee3.setTraineeId(7);
		trainee3.setUsername("jlp");
		traineeService.persistTrainee(trainee3);
		
		String newFirstName = "Danny";
		String newLastName = "Salvadori (the best)";
		trainee3.setFirstname(newFirstName);
		trainee3.setLastname(newLastName);
		traineeService.updateTrainee(trainee3);
		
		Trainee retrievedTrainee = traineeService.findTrainee(7);
		assertEquals(newFirstName, retrievedTrainee.getFirstname());
		assertEquals(newLastName, retrievedTrainee.getLastname());
	}
	
	@Test
	public void testTraineeServiceRemoveTraineeRemovesATraineeFromTheDatabase(){
		Trainee trainee2 = new Trainee();
		trainee2.setFirstname("James");
		trainee2.setLastname("Kirk");
		trainee2.setTraineeId(1);
		trainee2.setUsername("jk");
		traineeService.persistTrainee(trainee2);
		
		traineeService.removeTrainee(trainee2.getTraineeId());
		Trainee retrievedTrainee = traineeService.findTrainee(trainee2.getTraineeId());
		assertNull(retrievedTrainee);
	}
	
	@Test
	public void testTraineeServiceFindAllTraineesReturnsAListOfAllTraineesInTheDatabase(){
		
		Trainee trainee4 = new Trainee();
		trainee4.setTraineeId(3);
		trainee4.setFirstname("Bill");
		
		traineeService.persistTrainee(trainee4);
		
		List<Trainee> trainees= traineeService.findAllTrainees();

		assertTrue(trainees.contains(trainee4));
		
		
	}
	
	// TODO Error handling - JPA may throw several RuntimeExceptions.
	// Write tests to address these potential issues.

}
