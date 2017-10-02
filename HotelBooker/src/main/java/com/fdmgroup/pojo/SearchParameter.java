package com.fdmgroup.pojo;

import java.util.Date;

//import javax.validation.constraints.NotNull;

/**
 * Hold search parameters for calculating Options
 */
public class SearchParameter {

	private String locationString;
	
//	@NotNull
	private Integer numberOfGuests;
	
	private Date checkin;
	
	private Date checkout;
	
	private Double distance = 20.0;
	
	private double latitude;
	
	private double longitude;
	
	public SearchParameter() {
		System.out.println("SearchParameter default constuctor!");
	}
	
	public SearchParameter(String locationString, Integer numberOfGuests, Date checkin, Date checkout) {
		System.out.println("SearchParameter parameterised constuctor!");
		this.locationString = locationString;
		this.numberOfGuests = numberOfGuests;
		this.checkin = checkin;
		this.checkout = checkout;
	}
	
	/**
	 * TODO: Will populate latitude and longitude of specified location string
	 */
	public void findLatLong() {
		// use locationString
		latitude = 50.0;
		longitude = -0.9;
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

	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return "SearchParameter [locationString=" + locationString + ", numberOfGuests=" + numberOfGuests + ", checkin="
				+ checkin + ", checkout=" + checkout + ", distance=" + distance + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}
	
}
