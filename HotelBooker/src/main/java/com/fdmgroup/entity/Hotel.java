package com.fdmgroup.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fdmgroup.pojo.SearchParameter;
import com.fdmgroup.util.DateUtils;

/**
 * Mapping instructions for HotelService class
 */
@SqlResultSetMapping(
	name="HotelMapping",
	classes={
		@ConstructorResult(
			targetClass=Hotel.class,
            columns={
            	@ColumnResult(name="id", type=Integer.class),
                @ColumnResult(name="name", type=String.class),
                @ColumnResult(name="street", type=String.class),
                @ColumnResult(name="city", type=String.class),
                @ColumnResult(name="postcode", type=String.class),
            	@ColumnResult(name="latitude", type=Double.class),
            	@ColumnResult(name="longitude", type=Double.class),
            	@ColumnResult(name="distance", type=Double.class)
            }
		)
    }
)

@Entity
@Table(name = "Hotels")
public class Hotel {

	@Id
	@Column
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	private String street;
	
	@Column
	private String city;
	
	@Column
	private String postcode;
	
	@Column
	private Double latitude;
	
	@Column
	private Double longitude;
	
	/**
	 * Calculated database-side to rule out hotels too far away. Tracked to display to users
	 */
	@Transient
	private Double distance;
	
	/**
	 * Each room in the hotel, mapped by it's room ID
	 */
	@Transient
	private Map<Integer, Room> roomMap;
	
	/**
	 * Lists of room reservations, mapped to the ID of their respective room
	 */
	@Transient
	private Map<Integer, List<RoomReservation>> roomResMap;
	
	public Hotel() {}
	
	public Hotel(
		Integer id,
		String name,
		String street,
		String city,
		String postcode,
		Double latitude,
		Double longitude,
		Double distance
	) {
		this.id = id;
		this.name = name;
		this.street = street;
		this.city = city;
		this.postcode = postcode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
		this.roomMap = new HashMap<Integer, Room>();
		this.roomResMap = new HashMap<Integer, List<RoomReservation>>();
		//roomMap = // RoomService.(pass emf into this constructor?).findRoomsByHotel
	}
	
	/**
	 * Returns true if this Hotel instance has sufficient vacancies for a given search  
	 * @param param Instance of SearchParameter containing checkin and checkout dates and the number of guests 
	 * @return boolean
	 */
	public boolean isAvailable(SearchParameter param) {
		// Assumed false until shown otherwise
		Boolean isAvailable = false;
				
		// If the available rooms have enough capacity return true, else return false
		Integer availableCapacity = 0;
		for (Room room : getAvailableRooms(param)) {
			availableCapacity += room.getCapacity();
			if (availableCapacity >= param.getNumberOfGuests()) {
				isAvailable = true;
				break;
			}
		}
		return isAvailable;
	}
	
	/**
	 * Returns a list of this hotel's available rooms given search parameters  
	 * @param param Instance of SearchParameter containing checkin and checkout dates 
	 * @return List<Room> An list containing the available rooms for this hotel
	 */
	public List<Room> getAvailableRooms(SearchParameter param) {
		Set<Integer> availableRoomIdSet = getAvailableRoomIdSet(param);
		List<Room> availableRoomList = new ArrayList<Room>();
		for (Integer id : availableRoomIdSet) {
			availableRoomList.add(roomMap.get(id));
		}
		return availableRoomList;
	}
	
	/**
	 * Returns the Ids of the rooms that are available given search parameters  
	 * @param param Instance of SearchParameter containing checkin and checkout dates and the number of guests 
	 * @return Set<Integer> A Set containing the IDs of this hotel's available rooms
	 */
	private Set<Integer> getAvailableRoomIdSet(SearchParameter param) {
		// Get available rooms by removing those with conflicting reservations
		Set<Integer> availableRoomIdSet = roomMap.keySet();
		System.out.println("roomMap.size(): " + roomMap.size());
		System.out.println("roomResMap.size(): " + roomResMap.size());
		for (Integer roomId : roomResMap.keySet()) {
			for (RoomReservation roomRes : roomResMap.get(roomId)) {
				Date resDate = roomRes.getDate();
				System.out.println("---- CHECKING ROOM AVAILABILITY ----");
				System.out.println("resDate: " + resDate);
				System.out.println("p.checkin: " + param.getCheckin());
				System.out.println("p.checkout: " + param.getCheckout());
				System.out.println("resDate.after(param.getCheckin())? " + resDate.after(param.getCheckin()));
				System.out.println("resDate.before(param.getCheckout())? " + resDate.before(param.getCheckout()));
				if ( resDate.after(param.getCheckin()) && resDate.before(param.getCheckout())
						|| DateUtils.isSameDay(resDate, param.getCheckin())
						|| DateUtils.isSameDay(resDate, param.getCheckout())
						) {
					availableRoomIdSet.remove(roomId);
					continue;
				}
			}
		}
		return availableRoomIdSet;
	}
	
	/**
	 * Copies non-Id values from a given Hotel instance
	 * @param hotel
	 */
	public void clone(Hotel hotel) {
		this.name = hotel.getName();
		this.street = hotel.getStreet();
		this.city = hotel.getCity();
		this.postcode = hotel.getPostcode();
		this.latitude = hotel.getLatitude();
		this.longitude = hotel.getLongitude();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Map<Integer, Room> getRoomMap() {
		return roomMap;
	}

	public void setRoomMap(Map<Integer, Room> roomMap) {
		this.roomMap = roomMap;
	}

	public Map<Integer, List<RoomReservation>> getRoomResMap() {
		return roomResMap;
	}

	public void setRoomResMap(Map<Integer, List<RoomReservation>> roomResMap) {
		this.roomResMap = roomResMap;
	}
	
	@Override
	public String toString() {
		return "Hotel [id=" + id + ", name=" + name + ", street=" + street + ", city=" + city + ", postcode=" + postcode
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", distance=" + distance + ", roomMap="
				+ roomMap + ", roomResMap=" + roomResMap + "]";
	}
		
}
