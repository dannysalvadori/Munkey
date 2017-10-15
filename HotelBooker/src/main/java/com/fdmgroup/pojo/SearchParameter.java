package com.fdmgroup.pojo;

import java.util.Date;

import com.fdmgroup.util.LocationUtils;

/**
 * Holds search parameters for calculating options
 */
public class SearchParameter {

	private String locationString;
	
	private Integer numberOfGuests;
	
	private Date checkin;
	
	private Date checkout;
	
	private Double distance = 20.0;
	
	private Double latitude;
	
	private Double longitude;
	
	/**
	 * TODO: Will populate latitude and longitude of specified location string
	 */
	public void findLatLong() {
		// use locationString and geoCoding to get latitude and longitude
		String[] latLong;
		try {
			latLong = LocationUtils.getLatLongPositions(locationString);
			latitude = Double.valueOf(latLong[0]);
			longitude = Double.valueOf(latLong[1]);
		} catch (Exception e) {
			// TODO: Error handling for invalid location string input
			e.printStackTrace();
		}
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
	
	@Override
	public String toString() {
		return "SearchParameter [locationString=" + locationString + ", numberOfGuests=" + numberOfGuests + ", checkin="
				+ checkin + ", checkout=" + checkout + ", distance=" + distance + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}
	
}
