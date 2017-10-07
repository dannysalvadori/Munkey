package com.fdmgroup.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import com.fdmgroup.entity.Hotel;

public class HotelService {

	private EntityManagerFactory emf;

	public HotelService(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	/**
	 * Fetch Hotel from database by Id
	 * @param id
	 * @return Hotel identified by the given id
	 */
	public Hotel findHotel(int id) {
		Hotel hotel;
		EntityManager em = getEntityManager();
		try {
			hotel = em.find(Hotel.class, id);
		} finally {
			em.close();
		}
		return hotel;
	}
	
	/**
	 * Fetch all Hotels within a given distance of a given location
	 * @param latitude
	 * @param Longitude
	 * @param distanceKM (default 20km)
	 * @return List of Hotels within distanceKM of the given location
	 */
	public List<Hotel> findHotelsByLocation(double latitude, double longitude, Double distanceKM) {
		EntityManager em = getEntityManager();
		
		Query query = em.createNativeQuery(
			"SELECT id, name, street, city, postcode, latitude, longitude, "
				+ "GetDistance(Latitude, Longitude, :latitude, :longitude) AS distance "
			+ "FROM Hotels "
			+ "WHERE GetDistance(Latitude, Longitude, :latitude, :longitude) <= :dist"
			, "HotelMapping" // Object mapping instructions
		);
		query.setParameter("latitude", latitude);
		query.setParameter("longitude", longitude);
		query.setParameter("dist", distanceKM);
		
		List<Hotel> hotelList = query.getResultList();
		return hotelList;
	}
	
	/**
	 * Gets all Hotels. Used for testing only.
	 * @return
	 */
	public List<Hotel> findHotels() {
		EntityManager em = getEntityManager();
		
		Query query = em.createNativeQuery(
			"SELECT *"
			+ "FROM Hotels "
			, Hotel.class
		);
		
		List<Hotel> hotelList = query.getResultList();
		return hotelList;
	}
		
	/**
	 * Insert Hotel into the database
	 * @param hotel
	 * @return the persisted Hotel instance
	 */
	public Hotel persistHotel(Hotel hotel) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(hotel);
			et.commit();
		} finally {
			em.close();
		}
		return hotel;
	}
	
	/**
	 * Remove a Hotel from the database if it exists
	 * @param id
	 */
	public void removeHotel(int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		Hotel hotel = em.find(Hotel.class, id);
		try {
			if (hotel != null) {
				et.begin();
				em.remove(hotel);
				et.commit();
			}
		} finally {
			em.close();
		}
	}
	
	/**
	 * Update an existing Hotel instance
	 * @param hotel
	 */
	public void updateHotel(Hotel hotel) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		Hotel existingHotel = em.find(Hotel.class, hotel.getId());
		try {
			if (existingHotel != null) {
				et.begin();
				existingHotel.clone(hotel);
				et.commit();
			}
		} finally {
			em.close();
		}
	}

}
