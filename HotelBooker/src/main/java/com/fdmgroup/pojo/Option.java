package com.fdmgroup.pojo;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.entity.Hotel;
import com.fdmgroup.entity.Room;

public class Option {

	private String hotelName;
	
	private Double price;
	
	private String description;
	
	// May exceed required capacity; used in option calculation
	private Integer capacity;
	
	private List<Room> roomList;

	public String getHotelName() {
		return hotelName;
	}
	
	public Option() {
		super();
		roomList = new ArrayList<Room>();
	}
	
	public Option(Hotel h) {
		super();
		this.hotelName = h.getName();
		this.capacity = 0;
		roomList = new ArrayList<Room>();
	}

	/**
	 * Add room to list and count capacity
	 * @param room
	 */
	public void addRoom(Room room) {
		roomList.add(room);
		capacity += room.getCapacity();
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
	
}
