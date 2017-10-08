package com.fdmgroup.pojo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fdmgroup.entity.Hotel;
import com.fdmgroup.entity.Room;

public class Option {
	
	private Hotel hotel;

	private String hotelName;
	
	private Double price;
	
	private String description;
	
	/**
	 * May exceed capacity required by a search. Used to calculate the best option 
	 */
	private Integer capacity;
	
	/**
	 * The list of rooms proposed in this option
	 */
	private List<Room> roomList;

	
	public Option() {
		super();
		roomList = new ArrayList<Room>();
	}
	
	public Option(Hotel h) {
		super();
		this.hotel = h;
		this.hotelName = h.getName();
		this.capacity = 0;
		this.price = 0.0;
		roomList = new ArrayList<Room>();
	}

	/**
	 * Add a room to the room list and increment capacity accordingly
	 * @param room
	 */
	public void addRoom(Room room) {
		roomList.add(room);
		capacity += room.getCapacity();
		price += room.getPricePerNight();
	}
	
	/**
	 * Nested comparator class. Used to sort collections of Options by price (asc)
	 */
	public class PriceComparator implements Comparator<Option> {
	    public int compare(Option a, Option b) {
	    	int comparison;
	        if (a.getPrice() > b.getPrice()) {
	        	comparison = 1;
	        } else if (a.getPrice() == b.getPrice()) {
	        	comparison = 0;
	        } else {
	        	comparison = -1;
	        }
	        return comparison;
	    }
	}
	
	public String getDistance() {
		return String.format("%.1f", hotel.getDistance());//.intValue());
	}
	
	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public List<Room> getRoomList() {
		return roomList;
	}

	@Override
	public String toString() {
		return "Option [hotelName=" + hotelName + ", price=" + price + ", description=" + description + ", capacity="
				+ capacity + ", roomList=" + roomList + "]";
	}
	
}
