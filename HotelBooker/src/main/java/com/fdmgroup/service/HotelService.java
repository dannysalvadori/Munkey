package com.fdmgroup.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import com.fdmgroup.entity.Hotel;

public class HotelService {

	private EntityManagerFactory emf;

	public HotelService(EntityManagerFactory emf) {
		this.emf = emf;
	}

	// N.B: Don't forget to close connection once transaction is complete!
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	/**
	 * Fetch Reservation from database by Id
	 * @param id
	 * @return Reservation identified by the given id
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
	 * Insert Reservation into the database
	 * @param reservation
	 * @return the persisted Reservation instance
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
	 * Remove a Trainee from the database if they exist
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
	
//	/**
//	 * Update an existing User instance
//	 * @param user
//	 */
//	public void updateReservation(Reservation reservation) {
//		EntityManager em = getEntityManager();
//		EntityTransaction et = em.getTransaction();
//		Reservation existingReservation = em.find(Reservation.class, reservation.getId());
//		try {
//			if (existingReservation != null) {
//				et.begin();
//				existingReservation.clone(reservation);
//				et.commit();
//			}
//		} finally {
//			em.close();
//		}
//	}

}
