package com.fdmgroup.serviceTest;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.entity.Room;
import com.fdmgroup.entity.Hotel;
import com.fdmgroup.entity.Reservation;
import com.fdmgroup.entity.RoomReservation;
import com.fdmgroup.entity.User;
import com.fdmgroup.service.RoomService;
import com.fdmgroup.service.UserService;
import com.fdmgroup.util.DateUtils;
import com.fdmgroup.service.HotelService;
import com.fdmgroup.service.ReservationService;
import com.fdmgroup.service.RoomReservationService;

/**
 * @author User
 *
 */
public class RoomReservationServiceTest {
	
	private static RoomReservationService roomResService;
	private static int roomReservationId = 0;
	private static Room testRoom;
	private static Reservation testReservation;
	private static Date testDate;
	private static Hotel testHotel;
	
	@BeforeClass
	public static void setupClass(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hb_persistence_unit");
		roomResService = new RoomReservationService(emf);
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(2017, 07, 28);  // Aug 28th 2017
		testDate = calendar.getTime();

		// Prerequisite objects
		testHotel = new Hotel();
		testHotel.setId(500);
		new HotelService(emf).persistHotel(testHotel);
		User testUser = new User();
		testUser.setId(600);
		new UserService(emf).persistUser(testUser);
		
		// Create sample Room
		testRoom = new Room();
		testRoom.setId(300);
		testRoom.setHotel(testHotel);
		new RoomService(emf).persistRoom(testRoom);
		
		// Create sample Reservation
		testReservation = new Reservation();
		testReservation.setId(1000);
		testReservation.setHotel(testHotel);
		testReservation.setUser(testUser);
		new ReservationService(emf).persistReservation(testReservation);
	}
	
	@Test
	public void findRoomReservationGetsRoomReservationIfFoundTest() {
		roomReservationId++;
		RoomReservation testRoomReservation = createRoomReservation(roomReservationId);
		Integer expectedId = testRoomReservation.getId();
		roomResService.persistRoomReservation(testRoomReservation);
		
		RoomReservation retrievedRoomReservation = roomResService.findRoomReservation(expectedId);
		Integer retrievedId = retrievedRoomReservation.getId();
		assertEquals(expectedId, retrievedId);
	}
	
	@Test
	public void findRoomReservationReturnsNullIfNotFoundTest() {
		roomReservationId++;
		RoomReservation testRoomReservation = createRoomReservation(roomReservationId);
		roomResService.persistRoomReservation(testRoomReservation);
		
		RoomReservation retrievedRoomReservation = roomResService.findRoomReservation(-30);
		assertEquals(null, retrievedRoomReservation);
	}
	
	@Test
	public void persistRoomReservationInsertsNewRecordIntoDatabaseTest() {
		roomReservationId++;
		RoomReservation testRoomReservation = createRoomReservation(roomReservationId);
		roomResService.persistRoomReservation(testRoomReservation);
		
		RoomReservation retrievedRoomReservation = roomResService.findRoomReservation(roomReservationId);
		Date retrievedDate = retrievedRoomReservation.getDate();
		assertTrue("RoomReservation was not persisted or was persisted inaccurately",
			DateUtils.isSameDay(testDate, retrievedDate));
	}

	@Test
	public void updateRoomReservationAltersTheDetailsOfAnExistingRecordTest() {
		roomReservationId++;
		RoomReservation testRoomReservation = createRoomReservation(roomReservationId);
		roomResService.persistRoomReservation(testRoomReservation);
		
		Date newDate = DateUtils.createDate(27, 8, 2017);
		
		testRoomReservation.setDate(newDate);
		roomResService.updateRoomReservation(testRoomReservation);
		
		RoomReservation retrievedRoomReservation = roomResService.findRoomReservation(roomReservationId);
		assertTrue("Date was not updated", DateUtils.isSameDay(newDate, retrievedRoomReservation.getDate()));
	}
	
	@Test
	public void removeRoomReservationDeletesRecordFromDatabaseTest() {
		roomReservationId++;
		RoomReservation testRoomReservation = createRoomReservation(roomReservationId);
		roomResService.persistRoomReservation(testRoomReservation);
		
		// Check User was created successfully in the first place
		RoomReservation retrievedRoomReservation = roomResService.findRoomReservation(roomReservationId);
		assert(retrievedRoomReservation != null);
		
		roomResService.removeRoomReservation(roomReservationId);
		RoomReservation deletedRoomReservation = roomResService.findRoomReservation(roomReservationId);
		assert(deletedRoomReservation == null);
	}
	
	@Test
	public void findRoomReservationsByHotelMapsOccupiedRoomsByHotelTest() {
		roomReservationId++;
		RoomReservation testRoomReservation = createRoomReservation(roomReservationId);
		roomResService.persistRoomReservation(testRoomReservation);
		
		Date checkin = DateUtils.createDate(24, 8, 2017);
		Date checkout = DateUtils.createDate(29, 8, 2017);		
		
		Map<Integer, List<RoomReservation>> resultMap
			= roomResService.findRoomReservationsByHotel(
				testHotel.getId(), //500
				DateUtils.formatDate(checkin), //'24-AUG-17'
				DateUtils.formatDate(checkout) //'29-AUG-17'
		);
		
		Integer expectedRoomId = testRoom.getId();
		
		assertTrue("Returned null", resultMap != null);
		assertTrue("Returned no results", resultMap.keySet().size() > 0);
		assertTrue("Did not return results for the correct hotel", resultMap.keySet().contains(expectedRoomId));
	}
	
	/**
	 * Creates a sample RoomReservation instance
	 * @param id
	 * @return a new instance of Reservation with pre-defined column values  
	 */
	private RoomReservation createRoomReservation(int id) {
		RoomReservation r = new RoomReservation();
		r.setId(id);
		r.setRoom(testRoom);
		r.setReservation(testReservation);
		r.setDate(testDate);
		return r;
	}

}