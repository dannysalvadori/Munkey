package com.fdmgroup.serviceTest;

import static org.junit.Assert.*;

import java.util.List;
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
		roomId++;
		Room testRoom = createRoom(roomId);
		Integer testCapacity = testRoom.getCapacity();
		roomService.persistRoom(testRoom);
		
		Room retrievedRoom = roomService.findRoom(roomId);
		Integer retrievedCapacity = retrievedRoom.getCapacity();
		assertEquals(testCapacity, retrievedCapacity);
	}
	
	@Test
	public void findRoomsByHotelGetsRoomsIfFoundTest() {
		roomId++;
		
		testHotel = new Hotel();
		testHotel.setId(99);
		hotelService.persistHotel(testHotel);
		
		Room testRoom= createRoom(roomId);
		testRoom.setHotel(testHotel);
		Integer expectedId = testRoom.getId();
		roomService.persistRoom(testRoom);
		
		Map<Integer, Room> retrievedRoomMap = roomService.findRoomsByHotel(testHotel);
		assertEquals(1, retrievedRoomMap.size());
		assertTrue("Room Map did not contain expected Id", retrievedRoomMap.keySet().contains(expectedId));
	}
	
	@Test
	public void findRoomsByHotelReturnsEmptyListIfNoneFoundTest() {
		roomId++;
		testHotel = new Hotel();
		testHotel.setId(100);
		hotelService.persistHotel(testHotel);
		
		Map<Integer, Room> retrievedRoomMap = roomService.findRoomsByHotel(testHotel);
		assertEquals(0, retrievedRoomMap.size());
	}

	@Test
	public void updateRoomAltersTheDetailsOfAnExistingRoomTest() {
		roomId++;
		Room testRoom = createRoom(roomId);
		roomService.persistRoom(testRoom);
		
		Integer newCapacity = 5;
		testRoom.setCapacity(newCapacity);
		roomService.updateRoom(testRoom);
		
		Room retrievedRoom = roomService.findRoom(roomId);
		assertEquals(newCapacity, retrievedRoom.getCapacity());
	}
	
	@Test
	public void removeRoomDeletesRoomFromDatabaseTest() {
		roomId++;
		Room testRoom = createRoom(roomId);
		roomService.persistRoom(testRoom);
		
		// Check Room was created successfully in the first place
		Room retrievedRoom = roomService.findRoom(roomId);
		assert(retrievedRoom != null);
		
		roomService.removeRoom(roomId);
		Room deletedReservation = roomService.findRoom(roomId);
		assert(deletedReservation == null);
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
