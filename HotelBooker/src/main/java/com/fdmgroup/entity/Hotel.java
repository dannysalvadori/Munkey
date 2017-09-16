package com.fdmgroup.entity;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;

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
