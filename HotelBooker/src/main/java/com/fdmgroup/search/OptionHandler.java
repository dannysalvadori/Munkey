package com.fdmgroup.search;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;

import com.fdmgroup.entity.Hotel;
import com.fdmgroup.entity.Room;
import com.fdmgroup.pojo.Option;
import com.fdmgroup.pojo.SearchParameter;
import com.fdmgroup.service.HotelService;
import com.fdmgroup.service.RoomReservationService;
import com.fdmgroup.service.RoomService;
import com.fdmgroup.util.DateUtils;

public class OptionHandler {
		
	/**
	 * Returns a list of reservation options that are available for the given search parameters. 
	 * @param param Instance of SearchParameter
	 * @return
	 */
	public static List<Option> calculateOptions(SearchParameter param, EntityManagerFactory emf) {
		
		// Search location must be converted to latitude/longitude to find hotels 
		param.findLatLong();
		
		List<Option> optionList = new ArrayList<Option>();
		
		RoomReservationService roomResService = new RoomReservationService(emf);
		RoomService roomService = new RoomService(emf);
		
		// First query hotels that are in the right location
		List<Hotel> localHotelList = new HotelService(emf).findHotelsByLocation(
			param.getLatitude(),
			param.getLongitude(),
			param.getDistance()
		);
		
		for (Hotel localHotel : localHotelList) {
			// TODO: You REALLY shouldn't be executing SQL in a for loop
			localHotel.setRoomResMap(roomResService.findRoomReservationsByHotel(
				localHotel.getId(),
				DateUtils.formatDate(param.getCheckin()),
				DateUtils.formatDate(param.getCheckout()))
			);
			// TODO: You MUST be able to at least combine these queries. Get room res and all room detail, initialise both
			localHotel.setRoomMap(roomService.findRoomsByHotel(localHotel));
		}

		// Filter out hotels without sufficient vacancies
		List<Hotel> availableHotelList = new ArrayList<Hotel>();
		for (Hotel hotel : localHotelList) {
			if (hotel.isAvailable(param)) {
				availableHotelList.add(hotel);
			}
		}
		
		for (Hotel hotel : availableHotelList) {
			optionList.add(calculateCheapestOption(hotel, param));
		}
		
		// Sort by price and return
		optionList.sort(new Option(). new PriceComparator());
		return optionList;
	}
	
	/**
	 * Calculates the cheapest option for a given hotel with given parameters
	 * @param h Instance of Hotel
	 * @param p instance of SearchParameter
	 * @return
	 */
	private static Option calculateCheapestOption(Hotel h, SearchParameter p) {
		Option o = new Option(h);
		List<Room> roomList = h.getAvailableRooms(p);
		// TODO: backup checks that roomList isn't empty and that capacity is reached
		// Note this shouldn't come up because an AVAILABLE hotel will have done this check once already
		
		// Sort rooms by price per person (asc)
		roomList.sort(new Room(). new PPPComparator());
		// Meet the required capacity with the cheapest rooms
		while (o.getCapacity() < p.getNumberOfGuests()) {
			// Add room if it has less than or equal capacity to the remaining capacity to fill
			if (roomList.get(0).getCapacity() <= p.getNumberOfGuests() - o.getCapacity()) {
				o.addRoom(roomList.get(0));
				roomList.remove(0);
				
			} else { // If cheapest room has more capacity than needed, get cheapest overall room instead
				// Sort rooms by capacity (asc), then total price (asc)
				roomList.sort(new Room(). new CapacityThenPPNComparator());
				
				for (Room r : roomList) {
					if (r.getCapacity() >= p.getNumberOfGuests() - o.getCapacity()) {
						o.addRoom(r);
						break;
					}
				}
			}
		}
		return o;
	}
	
}
