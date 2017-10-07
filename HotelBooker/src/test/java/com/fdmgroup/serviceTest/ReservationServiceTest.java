package com.fdmgroup.serviceTest;

import static org.junit.Assert.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.entity.Hotel;
import com.fdmgroup.entity.Reservation;
import com.fdmgroup.entity.User;
import com.fdmgroup.service.HotelService;
import com.fdmgroup.service.ReservationService;
import com.fdmgroup.service.UserService;

public class ReservationServiceTest {
	
	private static ReservationService reservationService;
	private static int reservationId = 0;
	private static String testReference = "TEST_REFERENCE";
	private static Hotel testHotel;
	private static User testUser;
	
	@BeforeClass
	public static void setupClass(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hb_persistence_unit");
		reservationService = new ReservationService(emf);
		
		testHotel = new Hotel();
		testHotel.setId(5);
		new HotelService(emf).persistHotel(testHotel);
		
		testUser = new User();
		testUser.setId(1);
		new UserService(emf).persistUser(testUser);
	}

	@Test
	public void persistReservationInsertsNewUserIntoDatabaseTest() {
		// Create new reservation
		reservationId++;
		Reservation testReservation = createReservation(reservationId);
		String testReference = testReservation.getReference();
		reservationService.persistReservation(testReservation);
		
		// Confirm reservation got created
		Reservation retrievedReservation = reservationService.findReservation(reservationId);
		String retrievedReference = retrievedReservation.getReference();
		assertEquals(testReference, retrievedReference);
	}
	
	@Test
	public void findReservationByReferenceGetsReservationIfFoundTest() {
		// Create reservation
		reservationId++;
		Reservation testReservation = createReservation(reservationId);
		Integer expectedId = testReservation.getId();
		reservationService.persistReservation(testReservation);
		
		// Confirm reservation can be queried using reference
		Reservation retrievedReservation = reservationService.findReservationByReference(testReference+reservationId);
		Integer retrievedId = retrievedReservation.getId();
		assertEquals("Reservation id was incorrect", expectedId, retrievedId);
	}
	
	@Test
	public void findReservationByReferenceReturnsNullIfNotFoundTest() {
		// Create reservation
		reservationId++;
		Reservation testReservation = createReservation(reservationId);
		reservationService.persistReservation(testReservation);
		
		// Confirm that findReservation returns null if searching for an invalid reference
		Reservation retrievedReservation = reservationService.findReservationByReference("Bogus Reference");
		assertEquals("Retrieve output was incorrect", null, retrievedReservation);
	}

	@Test
	public void updateUserAltersTheDetailsOfAnExistingUserTest() {
		// Create reservation
		reservationId++;
		Reservation testReference = createReservation(reservationId);
		reservationService.persistReservation(testReference);
		
		// Update reservation's reference
		String newReference = "UPDATED";
		testReference.setReference(newReference);
		reservationService.updateReservation(testReference);
		
		// Confirm reference was updated in DB
		Reservation retrievedReservation = reservationService.findReservation(reservationId);
		assertEquals("Reference was incorrect", newReference, retrievedReservation.getReference());
	}
	
	@Test
	public void removeUserDeletesUserFromDatabaseTest() {
		// Create reservation
		reservationId++;
		Reservation testReservation = createReservation(reservationId);
		reservationService.persistReservation(testReservation);
		
		// Confirm reservation was created successfully
		Reservation retrievedReservation = reservationService.findReservation(reservationId);
		assert(retrievedReservation != null);
		
		// Remove reservation
		reservationService.removeReservation(reservationId);
		
		// Confirm reservation was deleted
		Reservation deletedReservation = reservationService.findReservation(reservationId);
		assertEquals("Reservation was not deleted", null, deletedReservation);
	}
	
	/**
	 * Creates a sample Reservation instance
	 * @param id
	 * @return a new instance of Reservation with pre-defined column values  
	 */
	private Reservation createReservation(int id) {
		Reservation r = new Reservation();
		r.setId(id);
		r.setReference(testReference+id);
		r.setHotel(testHotel);
		r.setUser(testUser);
		return r;
	}

}
