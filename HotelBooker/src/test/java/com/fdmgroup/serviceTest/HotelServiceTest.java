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
	public void findRoomsByHotelGetsRoomsIfFoundTest() {
		hotelId++;
		
		// Create a Hotel in the middle of the Altlantic Ocean where
		// no other hotels would realistically be
		String hotelName = "Middle of the Atlantic Hotel";
		Double hotelLatitude = 48.181557;
		Double hotelLongitude = -32.536252;
		
		Hotel unlikelyHotel = createHotel(hotelId);
		unlikelyHotel.setName(hotelName);
		unlikelyHotel.setLatitude(hotelLatitude);
		unlikelyHotel.setLongitude(hotelLongitude);
		hotelService.persistHotel(unlikelyHotel);
		
		List<Hotel> retrievedHotelList = hotelService.findHotelsByLocation(hotelLatitude, hotelLongitude, 20.0);
		assertEquals(1, retrievedHotelList.size());
		Hotel retrievedHotel = retrievedHotelList.get(0);
		assertEquals(hotelName, retrievedHotel.getName());
		assert(retrievedHotel.getDistance() == 0.0);
	}

	@Test
	public void persistHotelInsertsNewHotelIntoDatabaseTest() {
		hotelId++;
		Hotel testHotel = createHotel(hotelId);
		String testName = testHotel.getName();
		hotelService.persistHotel(testHotel);
		
		Hotel retrievedRoom = hotelService.findHotel(hotelId);
		String retrievedName = retrievedRoom.getName();
		assertEquals(testName, retrievedName);
	}

	@Test
	public void updateHotelAltersTheDetailsOfAnExistingHotelTest() {
		hotelId++;
		Hotel testHotel = createHotel(hotelId);
		hotelService.persistHotel(testHotel);
		
		String newName = "Updated Name";
		testHotel.setName(newName);
		hotelService.updateHotel(testHotel);
		
		Hotel retrievedRoom = hotelService.findHotel(hotelId);
		assertEquals(newName, retrievedRoom.getName());
	}
	
	@Test
	public void removeHotelDeletesHotelFromDatabaseTest() {
		hotelId++;
		Hotel testRoom = createHotel(hotelId);
		hotelService.persistHotel(testRoom);
		
		// Check Hotel was created successfully in the first place
		Hotel retrievedRoom = hotelService.findHotel(hotelId);
		assert(retrievedRoom != null);
		
		hotelService.removeHotel(hotelId);
		Hotel deletedReservation = hotelService.findHotel(hotelId);
		assert(deletedReservation == null);
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
