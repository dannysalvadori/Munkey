package com.fdmgroup.entityTest;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.entity.Hotel;
import com.fdmgroup.entity.Reservation;
import com.fdmgroup.entity.Room;
import com.fdmgroup.entity.RoomReservation;
import com.fdmgroup.entity.User;
import com.fdmgroup.pojo.SearchParameter;
import com.fdmgroup.service.HotelService;
import com.fdmgroup.service.ReservationService;
import com.fdmgroup.service.RoomReservationService;
import com.fdmgroup.service.RoomService;
import com.fdmgroup.service.UserService;
import com.fdmgroup.util.DateUtils;

public class HotelTest {

	private static EntityManagerFactory emf;
	private static HotelService hotelService;
	private static RoomService roomService;
	private static ReservationService resService;
	private static RoomReservationService roomResService;
	private static UserService userService;
	
	private static Hotel hotel;

	@BeforeClass
	public static void setup() {
		emf = Persistence.createEntityManagerFactory("hb_persistence_unit");
		hotelService = new HotelService(emf);
		roomResService = new RoomReservationService(emf);
		roomService = new RoomService(emf);
		resService = new ReservationService(emf);
		userService = new UserService(emf);
		
		hotel = new Hotel(-4,"TestHotel","x","x","x",50.0,-0.9,10.0);
		Room room = new Room(-20);
		room.setCapacity(4);
		room.setHotel(hotel);
		User user = new User();
		user.setId(-49);
		Reservation reservation = new Reservation(-40);
		reservation.setUser(user);
		reservation.setHotel(hotel);
		List<RoomReservation> roomResList = new ArrayList<RoomReservation>();
		for (int i = 1; i <= 3; i++) {
			roomResList.add(
				new RoomReservation(i,DateUtils.createDate(i, 1, 2017),room.getId(),reservation.getId(),hotel.getId())
			);
		}
		
		hotelService.persistHotel(hotel);
		roomService.persistRoom(room);
		userService.persistUser(user);
		resService.persistReservation(reservation);
		roomResService.persistRoomReservation(roomResList);
	}
	
	@Test
	public void isAvailableReturnsFalseIfThereAreNoRoomsTest() {
		SearchParameter param = new SearchParameter();
		param.setCheckin(DateUtils.createDate(1, 1, 2017));
		param.setCheckout(DateUtils.createDate(3, 1, 2017));
		param.setDistance(20.0);
		param.setLatitude(50.0);
		param.setLatitude(-9.0);
		// More guests than available
		param.setNumberOfGuests(500);
		assertFalse(hotel.isAvailable(param));
	}

}
