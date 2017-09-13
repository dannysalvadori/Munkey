package pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Persistence;

import com.fdmgroup.entity.Hotel;
import com.fdmgroup.entity.Room;
import com.fdmgroup.entity.RoomReservation;
import com.fdmgroup.service.RoomService;
import com.fdmgroup.util.DateUtils;

/**
 * @author User
 *
 */
public class AvailableHotel {
	
	// The Hotel this option is for
	private Hotel hotel;
	
	// Rooms at this hotel mapped by their Ids
	private Map<Integer, Room> roomMap;
	
	// Lists of room reservations mapped to their respective room Ids
	private Map<Integer, List<RoomReservation>> roomReservationMap;
	
	// Brief description of the number and types of rooms in this Option
	private String description;
	
	// The total price of the Option
	private Double price;
	
	private Option bestOption;
	
	/**
	 * Calculates the cheapest available deal for the input parameters
	 */
	public Option calculateBestOption(Integer numberOfGuests, Date checkin, Date checkout) {
		Option option = new Option();
		
		// Get available rooms for this Hotel
		List<Integer> availableRoomIdList = new ArrayList<Integer>();
		for (Integer roomId : roomReservationMap.keySet()) {
			for (RoomReservation roomRes : roomReservationMap.get(roomId)) {
				Date resDate = roomRes.getDate();
				if ( resDate.before(checkin) || resDate.after(checkout) ) {
					availableRoomIdList.add(roomId);
				}
			}
		}
		
		// Check the available rooms have enough capacity for the number of guests
		Boolean isOptionPossible = false;
		Integer availableCapacity = 0;
		for (Integer roomId : availableRoomIdList) {
			availableCapacity += roomMap.get(roomId).getCapacity();
			if (availableCapacity >= numberOfGuests) {
				isOptionPossible = true;
				break;
			}
		}
		
		List<Room> availableRoomList = new ArrayList<Room>();//new RoomService.findRoomsByIdList(availableRoomIdList);
		if (isOptionPossible) {
			//calculate best option
			
			//Sort rooms by price per person, tie-breaking on price overall
			//While option.Capacity < numberOfGuests...
			//    if roomList[0].getCapacity <= remainingSpaces()  // remaining spaces is numGuests - option.Capacity now
			//        option.roomList.add(roomList[0]);
			//    else... (if next cheapest room's capacity exceeds requirement)
			//        add cheapest room with enough capacity
			//        (do so by sorting room list by capacity (asc), then for each, if capacity >= remainingSpaces() option.add(room)
		}
		
		price = 30.24;
		description = "abc";
		
		this.bestOption = option;
		return option;
	}
	
}
