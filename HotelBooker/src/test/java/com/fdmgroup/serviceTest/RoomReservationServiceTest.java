package com.fdmgroup.serviceTest;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private static Integer roomReservationId = 0;
	private static Room testRoom;
	private static Reservation testReservation;
	private static Date testDate;
	private static Hotel testHotel;
	
	@BeforeClass
	public static void setupClass(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hb_persistence_unit");
		roomResService = new RoomReservationService(emf);

		// Date: 28th Aug 2017
		testDate = DateUtils.createDate(28, 8, 2017);

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
	public void persistRoomReservationInsertsNewRecordIntoDatabaseTest() {
		// Create room reservation
		roomReservationId++;
		RoomReservation testRoomReservation = createRoomReservation(roomReservationId);
		roomResService.persistRoomReservation(testRoomReservation);
		
		// Confirm room reservation was persisted
		RoomReservation retrievedRoomReservation = roomResService.findRoomReservation(roomReservationId);
		Date retrievedDate = retrievedRoomReservation.getDate();
		assertTrue("RoomReservation was not persisted correctly: date did not match",
				DateUtils.isSameDay(testDate, retrievedDate));
	}
	
	@Test
	public void findRoomReservationGetsRoomReservationIfFoundTest() {
		// Create room reservation
		roomReservationId++;
		RoomReservation testRoomReservation = createRoomReservation(roomReservationId);
		Integer expectedId = testRoomReservation.getId();
		roomResService.persistRoomReservation(testRoomReservation);
		
		// Confirm room can be retrieved by Id
		RoomReservation retrievedRoomReservation = roomResService.findRoomReservation(expectedId);
		Integer retrievedId = retrievedRoomReservation.getId();
		assertEquals("Id was incorrect", expectedId, retrievedId);
	}
	
	@Test
	public void findRoomReservationReturnsNullIfNotFoundTest() {
		// Create room reservation
		roomReservationId++;
		RoomReservation testRoomReservation = createRoomReservation(roomReservationId);
		roomResService.persistRoomReservation(testRoomReservation);
		
		// Attempt to retrieve a room by invalid Id
		RoomReservation retrievedRoomReservation = roomResService.findRoomReservation(-30);
		
		// Confirm retrieve result was null
		assertEquals("Retrieve return unexpected results", null, retrievedRoomReservation);
	}

	@Test
	public void updateRoomReservationAltersTheDetailsOfAnExistingRecordTest() {
		// Create room reservation
		roomReservationId++;
		RoomReservation testRoomReservation = createRoomReservation(roomReservationId);
		roomResService.persistRoomReservation(testRoomReservation);
		
		// Update room reservation's date
		Date newDate = DateUtils.createDate(27, 8, 2017);
		testRoomReservation.setDate(newDate);
		roomResService.updateRoomReservation(testRoomReservation);
		
		// Confirm date was updated in DB
		RoomReservation retrievedRoomReservation = roomResService.findRoomReservation(roomReservationId);
		assertTrue("Date was not updated", DateUtils.isSameDay(newDate, retrievedRoomReservation.getDate()));
	}
	
	@Test
	public void removeRoomReservationDeletesRecordFromDatabaseTest() {
		// Create room reservation
		roomReservationId++;
		RoomReservation testRoomReservation = createRoomReservation(roomReservationId);
		roomResService.persistRoomReservation(testRoomReservation);
		
		// Confirm the room reservation was created successfully
		RoomReservation retrievedRoomReservation = roomResService.findRoomReservation(roomReservationId);
		assert(retrievedRoomReservation != null);
		
		// Remove room reservation
		roomResService.removeRoomReservation(roomReservationId);
		
		// Confirm room reservation was removed from DB
		RoomReservation deletedRoomReservation = roomResService.findRoomReservation(roomReservationId);
		assertEquals("Room reservation was not removed", null, deletedRoomReservation);
	}
	
	@Test
	public void findRoomReservationsByHotelMapsOccupiedRoomsByHotelTest() {
		// Create room reservation
		roomReservationId++;
		RoomReservation testRoomReservation = createRoomReservation(roomReservationId);
		roomResService.persistRoomReservation(testRoomReservation);
		
		// Create date parameters that surround the created reservation
		Date checkin = DateUtils.createDate(24, 8, 2017);
		Date checkout = DateUtils.createDate(29, 8, 2017);		
		
		// Get room reservations mapped by room Id for the reservation's hotel
		Map<Integer, List<RoomReservation>> resultMap
			= roomResService.findRoomReservationsByHotel(
				testHotel.getId(), //500
				DateUtils.formatDate(checkin), // 24-AUG-17
				DateUtils.formatDate(checkout) // 29-AUG-17
		);
		
		// Confirm the results include the created room reservation
		Integer expectedRoomId = testRoom.getId();
		assertTrue("Returned null", resultMap != null);
		assertTrue("Returned no results", resultMap.keySet().size() > 0);
		assertTrue("Did not return results for the correct hotel", resultMap.keySet().contains(expectedRoomId));
		List<RoomReservation> roomReservationList = resultMap.get(expectedRoomId);
		assertTrue("Room had no reservations", roomReservationList.size() > 0);
		Set<Integer> roomResIdSet = new HashSet<Integer>();
		for (RoomReservation roomRes : roomReservationList) {
			roomResIdSet.add(roomRes.getId());
		}
		assertTrue("Room reservations did not include sample ("+roomReservationId+")",
				roomResIdSet.contains(roomReservationId));
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