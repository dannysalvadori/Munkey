package com.fdmgroup.entity;

import java.util.Collections;
import java.util.Comparator;

import javax.persistence.*;

import com.fdmgroup.entity.Room.CapacityThenPPNComparator;

@Entity
@Table(name = "Rooms")
public class Room {

	@Id
	@Column
	private Integer id;

	@ManyToOne(optional=false, targetEntity=Hotel.class) 
	@JoinColumn(name="hotel_id", nullable=false, updatable=false)
	private Hotel hotel;
	
	@Column
	private Integer capacity;
	
	@Column(name="double_beds")
	private Integer doubleBeds;
	
	@Column(name="single_beds")
	private Integer singleBeds;
	
	@Column(name="price_per_night")
	private double pricePerNight;
	
	@Column(name="price_per_person")
	private double pricePerPerson;
	
	public Room() {}
	
	public Room (Integer id) {
		this.id = id;
	}
	
	// Copies non-Id column values from a given User object
	public void clone(Room room) {
		this.hotel = room.getHotel();
		this.capacity = room.getCapacity();
		this.doubleBeds = room.getDoubleBeds();
		this.singleBeds = room.getSingleBeds();
		this.pricePerNight = room.getPricePerNight();
		this.pricePerPerson = room.getPricePerPerson();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getDoubleBeds() {
		return doubleBeds;
	}

	public void setDoubleBeds(Integer doubleBeds) {
		this.doubleBeds = doubleBeds;
	}

	public Integer getSingleBeds() {
		return singleBeds;
	}

	public void setSingleBeds(Integer singleBeds) {
		this.singleBeds = singleBeds;
	}

	public double getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
		if (capacity > 0) {
			this.pricePerPerson = pricePerNight / capacity; 
		}
	}

	public double getPricePerPerson() {
		return pricePerPerson;
	}

	public void setPricePerPerson(double pricePerPerson) {
		this.pricePerPerson = pricePerPerson;
		if (capacity > 0) {
			this.pricePerNight = pricePerPerson * capacity; 
		}
	}
	
	public class PPPComparator implements Comparator<Room> {
	    // Used for sorting in ascending order of price per person
	    public int compare(Room a, Room b) {
	    	int comparison;
	        if (a.getPricePerPerson() > b.getPricePerPerson()) {
	        	comparison = 1;
	        } else if (a.getPricePerPerson() == b.getPricePerPerson()) {
	        	comparison = 0;
	        } else {
	        	comparison = -1;
	        }
	        return comparison;
	    }
	}
	
	// Collections.sort(roomList, new Room().new CapacityThenPPNComparator());
	public class CapacityThenPPNComparator implements Comparator<Room> {
	    // Used for sorting in ascending order of price per person
	    public int compare(Room a, Room b) {
	    	int comparison;
	        if (a.getCapacity() > b.getCapacity()) {
	        	comparison = 1;
	        } else if (a.getCapacity() < b.getCapacity()) {
	        	comparison = -1;
	        } else {
	        	if (a.getPricePerNight() > b.getPricePerNight()) {
	        		comparison = 1;	        		
	        	} else if (a.getPricePerNight() < b.getPricePerNight()) {
	        		comparison = -1;
	        	} else {
	        		comparison = 0;
	        	}
	        }
	        return comparison;
	    }
	}
	
}
