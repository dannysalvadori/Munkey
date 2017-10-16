package com.fdmgroup.entityTest;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.entity.Room;

public class RoomTest {

	private ArrayList<Room> roomList;

	/**
	 * Creates a number of rooms to be sorted in tests
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Create list of rooms in no particular order
		roomList = new ArrayList<Room>();
		roomList.add(createRoom(6, 40.0));
		roomList.add(createRoom(2, 50.0));
		roomList.add(createRoom(1, 10.0));
		roomList.add(createRoom(5, 70.0));
		roomList.add(createRoom(3, 20.0));
		roomList.add(createRoom(7, 30.0));
		roomList.add(createRoom(4, 60.0));
		roomList.add(createRoom(7, 65.0));
	}

	/**
	 * Price per Person Comparator should sort a collection of Rooms by price per person (ascending)
	 */
	@Test
	public void pppComparatorSortsByPricePerPersonAscendingTest() {
		roomList.sort(new Room().new PPPComparator());
		assertEquals("Room #1 price was incorrect", 10.0, roomList.get(0).getPricePerPerson(), 0.001);
		assertEquals("Room #2 price was incorrect", 20.0, roomList.get(1).getPricePerPerson(), 0.001);
		assertEquals("Room #3 price was incorrect", 30.0, roomList.get(2).getPricePerPerson(), 0.001);
		assertEquals("Room #4 price was incorrect", 40.0, roomList.get(3).getPricePerPerson(), 0.001);
		assertEquals("Room #5 price was incorrect", 50.0, roomList.get(4).getPricePerPerson(), 0.001);
		assertEquals("Room #6 price was incorrect", 60.0, roomList.get(5).getPricePerPerson(), 0.001);
		assertEquals("Room #7 price was incorrect", 65.0, roomList.get(6).getPricePerPerson(), 0.001);
		assertEquals("Room #7 price was incorrect", 70.0, roomList.get(7).getPricePerPerson(), 0.001);
	}
	
	/**
	 * Capacity then Price per Night Comparator should sort a collection by capacity (ascending), tie-breaking on 
	 * price per night (ascending).
	 */
	@Test
	public void capacityThenPPNComparatorSortsByCapacityASCThenByTotalPriceASCTest() {
		roomList.sort(new Room().new CapacityThenPPNComparator());
		assertTrue("Room #1 capacity was incorrect", roomList.get(0).getCapacity() == 1);
		assertTrue("Room #2 capacity was incorrect", roomList.get(1).getCapacity() == 2);
		assertTrue("Room #3 capacity was incorrect", roomList.get(2).getCapacity() == 3);
		assertTrue("Room #4 capacity was incorrect", roomList.get(3).getCapacity() == 4);
		assertTrue("Room #5 capacity was incorrect", roomList.get(4).getCapacity() == 5);
		assertTrue("Room #6 capacity was incorrect", roomList.get(5).getCapacity() == 6);
		assertTrue("Room #7 capacity was incorrect", roomList.get(6).getCapacity() == 7);
		assertTrue("Room #7 capacity was incorrect", roomList.get(7).getCapacity() == 7);
		
		assertEquals("Room #1 price was incorrect", 10.0,  roomList.get(0).getPricePerNight(), 0.001);
		assertEquals("Room #2 price was incorrect", 100.0, roomList.get(1).getPricePerNight(), 0.001);
		assertEquals("Room #3 price was incorrect", 60.0,  roomList.get(2).getPricePerNight(), 0.001);
		assertEquals("Room #4 price was incorrect", 240.0, roomList.get(3).getPricePerNight(), 0.001);
		assertEquals("Room #5 price was incorrect", 350.0, roomList.get(4).getPricePerNight(), 0.001);
		assertEquals("Room #6 price was incorrect", 240.0, roomList.get(5).getPricePerNight(), 0.001);
		assertEquals("Room #7 price was incorrect", 210.0, roomList.get(6).getPricePerNight(), 0.001);
		assertEquals("Room #7 price was incorrect", 455.0, roomList.get(7).getPricePerNight(), 0.001);
	}

	/**
	 * Creates a room object. Note price per NIGHT is set at capacity*pricePerPerson
	 */
	private Room createRoom(int capacity, double pricePerPerson) {
		Room room = new Room();
		room.setCapacity(capacity);
		room.setPricePerPerson(pricePerPerson);
		room.setPricePerNight(capacity*pricePerPerson);
		return room;
	}

}
