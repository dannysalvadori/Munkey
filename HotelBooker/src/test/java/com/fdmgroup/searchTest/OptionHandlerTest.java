/**
 * 
 */
package com.fdmgroup.searchTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.entity.Hotel;
import com.fdmgroup.entity.Reservation;
import com.fdmgroup.entity.Room;
import com.fdmgroup.entity.RoomReservation;
import com.fdmgroup.entity.User;
import com.fdmgroup.pojo.Option;
import com.fdmgroup.pojo.SearchParameter;
import com.fdmgroup.search.OptionHandler;
import com.fdmgroup.service.HotelService;
import com.fdmgroup.service.ReservationService;
import com.fdmgroup.service.RoomReservationService;
import com.fdmgroup.service.RoomService;
import com.fdmgroup.service.UserService;
import com.fdmgroup.util.DateUtils;

/**
 * @author User
 *
 */
public class OptionHandlerTest {
	
	private static HotelService hotelService;
	private static RoomService roomService;
	private static ReservationService resService;
	private static RoomReservationService roomResService;
	private static UserService userService;
	
	private static Hotel testHotel;
	private static Room testRoom;
	private static Reservation testRes;
	private static List<RoomReservation> testRoomResList;
	private static User testUser;
	
	private static EntityManagerFactory emf;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		emf = Persistence.createEntityManagerFactory("hb_persistence_unit");
		hotelService = new HotelService(emf);
		roomResService = new RoomReservationService(emf);
		roomService = new RoomService(emf);
		resService = new ReservationService(emf);
		userService = new UserService(emf);
		
		testHotel = new Hotel(-1, "TestHotel", "Test Street", "Test City", "TE5 T01", 50.0, -0.9, null);
		hotelService.persistHotel(testHotel);
		
		testRoom = new Room(-10);
		testRoom.setCapacity(4);
		testRoom.setDoubleBeds(2);
		testRoom.setHotel(testHotel);
		testRoom.setPricePerNight(60.0);
		roomService.persistRoom(testRoom);
		
		testUser = new User();
		testUser.setId(-50);
		userService.persistUser(testUser);
		
		testRes = new Reservation(-100);
		testRes.setStartDate(DateUtils.createDate(1, 1, 2000));
		testRes.setEndDate(DateUtils.createDate(5, 1, 2000));
		testRes.setNumberOfGuests(4);
		testRes.setHotel(testHotel);
		testRes.setUser(testUser);
		testRes.setReference("Test001");
		resService.persistReservation(testRes);
		
		testRoomResList = new ArrayList<RoomReservation>();
		for (int i = 1; i <= 5; i++) {
			RoomReservation roomRes = new RoomReservation();
			roomRes.setId(i);
			roomRes.setDate(DateUtils.createDate(i, 1, 2000));
			roomRes.setRoom(testRoom);
			roomRes.setReservation(testRes);
			testRoomResList.add(roomRes);
		}
		roomResService.persistRoomReservation(testRoomResList);
	}

	@Test
	public void generateOptionsReturnsListOfOptionsTest() {
		// Data is created in @Before method
		// Create search parameters
		Date checkin = DateUtils.createDate(1, 1, 2000);
		Date checkout = DateUtils.createDate(5, 1, 2000);
		SearchParameter param = new SearchParameter();
		param.setLocationString("Hatfield");
		param.setNumberOfGuests(4);
		param.setCheckin(checkin);
		param.setCheckout(checkout);
		param.setDistance(20000000.0);
		param.findLatLong();

		// Start test
		Object resultObj = OptionHandler.calculateOptions(param, emf);
		
		// Confirm expected results
		assertTrue("calculateOptions did not return a list", resultObj instanceof List<?>);
		List<Object> resultList = (List<Object>) resultObj;
		assertEquals("Got wrong number of results", 1, resultList.size());
		assertTrue("Result list was not of type Option", resultList.get(0) instanceof Option);
		Option result = (Option) resultList.get(0);
		assertTrue("Price was not correct", testRoom.getPricePerNight() == result.getPrice()); 
	}

}
