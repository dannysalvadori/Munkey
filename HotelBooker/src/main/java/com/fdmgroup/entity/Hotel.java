package com.fdmgroup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	
//	TODO: This should be @Transient but it's breaking the code
	private Double distance;
	
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
