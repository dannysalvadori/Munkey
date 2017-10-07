package com.fdmgroup.serviceTest;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.entity.Hotel;
import com.fdmgroup.service.HotelService;

public class HotelServiceTest {
	
	private static HotelService hotelService;
	private static int hotelId = 0;
	private static Hotel testHotel;
	
	@BeforeClass
	public static void setupClass(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hb_persistence_unit");
		hotelService = new HotelService(emf);
		
		testHotel = createHotel(hotelId);
		hotelService.persistHotel(testHotel);
	}
		
	@Test
	public void persistHotelInsertsNewHotelIntoDatabaseTest() {
		// Create new hotel
		Integer testId = ++hotelId;
		Hotel testHotel = createHotel(testId);
		String testName = testHotel.getName();
		
		// Persist
		hotelService.persistHotel(testHotel);
		
		// Confirm hotel got persisted
		Hotel retrievedHotel = hotelService.findHotel(hotelId);
		String retrievedName = retrievedHotel.getName();
		Integer retrievedId = retrievedHotel.getId();
		assertEquals("Hotel Id was incorrect", testId, retrievedId);
		assertEquals("Hotel name was incorrect", testName, retrievedName);
	}

	@Test
	public void findHotelsByLocationGetsHotelsWithinDistanceTest() {
		// Create a sample Hotel record
		String hotelName = "Middle of the Atlantic Hotel";
		Double hotelLatitude = 48.181557;
		Double hotelLongitude = -32.536252;
		Hotel unlikelyHotel = createHotel(++hotelId);
		unlikelyHotel.setName(hotelName);
		unlikelyHotel.setLatitude(hotelLatitude);
		unlikelyHotel.setLongitude(hotelLongitude);
		hotelService.persistHotel(unlikelyHotel);
		
		// Attempt to retrieve persisted hotel
		List<Hotel> retrievedHotelList = hotelService.findHotelsByLocation(hotelLatitude, hotelLongitude, 20.0);
		
		// Confirm hotel values are as expected
		assertEquals(1, retrievedHotelList.size());
		Hotel retrievedHotel = retrievedHotelList.get(0);
		assertEquals("Hotel name was incorrect", hotelName, retrievedHotel.getName());
		assertEquals("Hotel location was incorrect",
				Double.valueOf(0.0), Double.valueOf(retrievedHotel.getDistance()));
	}

	@Test
	public void updateHotelAltersTheDetailsOfAnExistingHotelTest() {
		// Create sample hotel
		Integer testId = ++hotelId;
		Hotel testHotel = createHotel(testId);
		hotelService.persistHotel(testHotel);
		
		// Update hotel name
		String newName = "Updated Name";
		testHotel.setName(newName);
		hotelService.updateHotel(testHotel);
		
		// Confirm hotel's name was changed
		Hotel retrievedRoom = hotelService.findHotel(testId);
		assertEquals("Hotel name was incorrect", newName, retrievedRoom.getName());
	}
	
	@Test
	public void removeHotelDeletesHotelFromDatabaseTest() {
		// Create hotel
		Integer testId = ++hotelId;
		Hotel testRoom = createHotel(testId);
		hotelService.persistHotel(testRoom);
		
		// Confirm hotel was created successfully
		Hotel retrievedRoom = hotelService.findHotel(testId);
		assert(retrievedRoom != null);
		
		// Remove hotel
		hotelService.removeHotel(testId);
		
		// Confirm hotel was removed
		Hotel deletedReservation = hotelService.findHotel(testId);
		assertEquals("Hotel was not deleted", null, deletedReservation);
	}
	
	/**
	 * Creates a sample Hotel instance
	 * @param id
	 * @return a new instance of Hotel with pre-defined column values  
	 */
	private static Hotel createHotel(int id) {
		Hotel h = new Hotel();
		h.setId(id);
		h.setName("Hotel " + id);
		h.setStreet("Test Street");
		h.setCity("Testville");
		h.setPostcode("TE57OI");
		h.setLatitude(51.5060651);
		h.setLongitude(-0.0869111);
		return h;
	}

}
