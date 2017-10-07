package com.fdmgroup.serviceTest;

import static org.junit.Assert.*;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.entity.Hotel;
import com.fdmgroup.entity.Room;
import com.fdmgroup.service.HotelService;
import com.fdmgroup.service.RoomService;

public class RoomServiceTest {
	
	private static RoomService roomService;
	private static HotelService hotelService;
	private static int roomId = 0;
	private static Hotel testHotel;
	
	@BeforeClass
	public static void setupClass(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hb_persistence_unit");
		roomService = new RoomService(emf);
		hotelService = new HotelService(emf);
		
		testHotel = new Hotel();
		testHotel.setId(5);
		hotelService.persistHotel(testHotel);
	}

	@Test
	public void persistRoomInsertsNewRoomIntoDatabaseTest() {
		// Create room
		Integer testId = ++roomId;
		Room testRoom = createRoom(testId);
		Integer testCapacity = testRoom.getCapacity();
		roomService.persistRoom(testRoom);
		
		// Confirm room was persisted in DB
		Room retrievedRoom = roomService.findRoom(testId);
		Integer retrievedCapacity = retrievedRoom.getCapacity();
		Integer retrievedId = retrievedRoom.getId();
		assertEquals("Room Id was incorrect", testId, retrievedId);
		assertEquals("Room capcity was incorrect", testCapacity, retrievedCapacity);
	}
	
	@Test
	public void findRoomsByHotelGetsRoomsIfFoundTest() {
		// Create test hotel
		roomId++;
		testHotel = new Hotel();
		testHotel.setId(99);
		hotelService.persistHotel(testHotel);
		
		// Create room
		Room testRoom= createRoom(roomId);
		testRoom.setHotel(testHotel);
		Integer expectedId = testRoom.getId();
		roomService.persistRoom(testRoom);
		
		// Confirm retrieve gets rooms mapped by their Ids
		Map<Integer, Room> retrievedRoomMap = roomService.findRoomsByHotel(testHotel);
		assertEquals("Room map was wrong size", 1, retrievedRoomMap.size());
		assertTrue("Room Map did not contain expected Id", retrievedRoomMap.keySet().contains(expectedId));
	}
	
	@Test
	public void findRoomsByHotelReturnsEmptyListIfNoneFoundTest() {
		// Create hotel, but no rooms
		roomId++;
		testHotel = new Hotel();
		testHotel.setId(100);
		hotelService.persistHotel(testHotel);
		
		// Confirm retrieve gets empty list
		Map<Integer, Room> retrievedRoomMap = roomService.findRoomsByHotel(testHotel);
		assertEquals("Got wrong number of rooms", 0, retrievedRoomMap.size());
	}

	@Test
	public void updateRoomAltersTheDetailsOfAnExistingRoomTest() {
		// Create room
		roomId++;
		Room testRoom = createRoom(roomId);
		roomService.persistRoom(testRoom);
		
		// Update room
		Integer newCapacity = 5;
		testRoom.setCapacity(newCapacity);
		roomService.updateRoom(testRoom);
		
		// Confirm DB room was updated
		Room retrievedRoom = roomService.findRoom(roomId);
		assertEquals("Capacity was incorrect", newCapacity, retrievedRoom.getCapacity());
	}
	
	@Test
	public void removeRoomDeletesRoomFromDatabaseTest() {
		// Create room
		roomId++;
		Room testRoom = createRoom(roomId);
		roomService.persistRoom(testRoom);
		
		// Confirm Room was created successfully
		Room retrievedRoom = roomService.findRoom(roomId);
		assert(retrievedRoom != null);
		
		// Delete room
		roomService.removeRoom(roomId);
		
		// Confirm room was deleted on DB
		Room deletedRoom = roomService.findRoom(roomId);
		assertEquals("Room was not deleted", null, deletedRoom);
	}
	
	/**
	 * Creates a sample Room instance
	 * @param id
	 * @return a new instance of Room with pre-defined column values  
	 */
	private Room createRoom(int id) {
		Room r = new Room();
		r.setId(id);
		r.setHotel(testHotel);
		r.setCapacity(4);
		r.setDoubleBeds(2);
		r.setSingleBeds(0);
		r.setPricePerNight(40);
		return r;
	}

}
