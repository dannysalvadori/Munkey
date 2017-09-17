package com.fdmgroup.pojo;

public class Option {

	private String hotelName;
	
	private Double price;
	
	private String description;
	
	// May exceed required capacity; used in option calculation
	private Integer capacity;

	public String getHotelName() {
		return hotelName;
	}
	
	// Some kind of constructor...

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
	
}
