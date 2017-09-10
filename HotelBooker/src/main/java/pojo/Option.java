package pojo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fdmgroup.entity.Hotel;
import com.fdmgroup.entity.Room;
import com.fdmgroup.entity.RoomReservation;

/**
 * @author User
 *
 */
public class Option {
	
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
	
	/**
	 * Calculates the cheapest available deal for the input parameters
	 */
	public void calculateBestOption(Integer numberOfGuests, Date checkin, Date checkout) {
		// insert calculation logic with help from some kind of helper class, one assumes
		price = 30.24;
		description = "abc";
	}
	
}
