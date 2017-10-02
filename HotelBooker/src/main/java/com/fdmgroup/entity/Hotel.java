package com.fdmgroup.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
	
	@Transient
	private Double distance;
	
	@Transient
	private Map<Integer, Room> roomMap;
	
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
		// Assumed false
		Boolean isAvailable = false;
		
		// Get available rooms for this Hotel
		Set<Integer> availableRoomIdSet = roomMap.keySet(); 
		for (Integer roomId : roomResMap.keySet()) {
			System.out.println("Iterating over something it shouldnt!!");
			for (RoomReservation roomRes : roomResMap.get(roomId)) {
				Date resDate = roomRes.getDate();
				if ( resDate.after(param.getCheckin()) && resDate.before(param.getCheckout())
						|| DateUtils.isSameDay(resDate, param.getCheckin())
						|| DateUtils.isSameDay(resDate, param.getCheckout())
				) {
					availableRoomIdSet.remove(roomId);
					continue;
				}
			}
		}
		
		// Check the available rooms have enough capacity for the number of guests
		Integer availableCapacity = 0;
		for (Integer roomId : availableRoomIdSet) {
			availableCapacity += roomMap.get(roomId).getCapacity();
			if (availableCapacity >= param.getNumberOfGuests()) {
				isAvailable = true;
				break;
			}
		}
		return isAvailable;
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

	// Copies non-Id values from a given Hotel instance
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
		
}
