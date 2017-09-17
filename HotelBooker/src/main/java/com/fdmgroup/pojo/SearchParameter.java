package com.fdmgroup.pojo;

import java.util.Date;

/**
 * Hold search parameters for calculating Options
 */
public class SearchParameter {

	private Integer numberOfGuests;
	
	private Date checkin;
	
	private Date checkout;
	
	private String locationString;
	
	private Double distance = 20.0;
	
	private Double latitude;
	
	private Double longitude;
	
	public SearchParameter() {
		System.out.println("I got instantiated!");
	}
	
	public SearchParameter(String locationString, Integer numberOfGuests, Date checkin, Date checkout) {
		setLatLong(locationString);
		this.locationString = locationString;
		this.numberOfGuests = numberOfGuests;
		this.checkin = checkin;
		this.checkout = checkout;
	}
	
	/**
	 * TODO: Will populate latitude and longitude of specified location string
	 */
	private void setLatLong(String location) {
		//latitude = abc;
		//longitude = xyz;
	}

	public Integer getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(Integer numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	public Date getCheckin() {
		return checkin;
	}

	public void setCheckin(Date checkin) {
		this.checkin = checkin;
	}

	public Date getCheckout() {
		return checkout;
	}

	public void setCheckout(Date checkout) {
		this.checkout = checkout;
	}

	public String getLocationString() {
		return locationString;
	}

	public void setLocationString(String locationString) {
		this.locationString = locationString;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
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
	
}
