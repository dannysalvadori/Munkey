package com.fdmgroup.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.fdmgroup.entity.Hotel;
import com.fdmgroup.entity.Room;
import com.fdmgroup.entity.RoomReservation;
import com.fdmgroup.pojo.Option;
import com.fdmgroup.pojo.SearchParameter;
import com.fdmgroup.service.HotelService;

public class OptionHandler {
	
	public static List<AvailableHotel> createAvailableHotels(Map<Integer, List<RoomReservation>> hotelReservationMap) {
		
		return null;
	}
	
	public static List<Option> calculateOptions(SearchParameter param) {
		List<Option> optionList = new ArrayList<Option>();
		
		//...
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hb_persistence_unit");
		List<Hotel> hotelList = new HotelService(emf).findHotelsByLocation(
			param.getLatitude(),
			param.getLongitude(),
			param.getDistance()
		);
		
		Map<Integer, Map<Integer, List<RoomReservation>>> hotelRoomRoomResMap
			= new HashMap<Integer, Map<Integer, List<RoomReservation>>>();
		Map<Integer, Room> roomMap = new HashMap<Integer, Room>();
		// Query all room reservations for these hotels and create AvailableHotel, passing in Map of Room Id to list of
		// room reservations
		
		/* Hotel    Room    Res
		 * 1        1       1
		 * 1        2       
		 * 2        1       1 ...
		 * For each row, if doesn't contain hotelId: Map<Id, Map<Id, List<RoomReservation>>>.put(hotelId, new Map<<>>)
		 *     If (NOT -- Map.get(hotelId).contains(roomId)) map.get(hotelId).add(RoomId)
		 *     map(hotelId)(roomId).add(roomreservation).
		 * 
		 * Then for each hotelId in map, run static AvailableHotel method to check if available (num guests, checkin/checkout)
		 * Needs to also pass in the master map, as well as a map of Room Ids to the Rooms themselves, for capacity.
		 * 
		 */
		
		List<AvailableHotel> availableHotelList = new ArrayList<AvailableHotel>();
		for (Integer hotelId : hotelRoomRoomResMap.keySet()) {
			if (AvailableHotel.isAvailable(
				roomMap,
				hotelRoomRoomResMap.get(hotelId),
				param.getNumberOfGuests(),
				param.getCheckin(),
				param.getCheckout())
			) {
				availableHotelList.add(new AvailableHotel(/*Some kind of parameters*/));
			}
		}
		
		return optionList;
	}
	
}
