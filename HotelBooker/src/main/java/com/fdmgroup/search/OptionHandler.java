package com.fdmgroup.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fdmgroup.entity.RoomReservation;
import com.fdmgroup.pojo.AvailableHotel;
import com.fdmgroup.pojo.Option;
import com.fdmgroup.pojo.SearchParameter;

public class OptionHandler {
	
	public static List<AvailableHotel> createAvailableHotels(Map<Integer, List<RoomReservation>> hotelReservationMap) {
		
		return null;
	}
	
	public static List<Option> calculateOptions(SearchParameter searchParameters) {
		List<Option> optionList = new ArrayList<Option>();
		//...
		
		// for each Hotel
		// get all rooms
		// remove all rooms with bookings in due date
		// if available rooms sum capacity < number of guests, remove Hotel
		// else Map available room to that hotel and calculate
		
		return optionList;
	}
	
}
