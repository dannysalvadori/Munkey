package com.fdmgroup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.fdmgroup.entity.RoomReservation;

public class RoomReservationService {

	private EntityManagerFactory emf;

	public RoomReservationService(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	/**
	 * Fetch RoomReservation from database by Id
	 * @param id
	 * @return RoomReservation identified by the given id
	 */
	public RoomReservation findRoomReservation(int id) {
		RoomReservation roomReservation;
		EntityManager em = getEntityManager();
		try {
			roomReservation = em.find(RoomReservation.class, id);
		} finally {
			em.close();
		}
		return roomReservation;
	}
	
	/**
	 * Inserts a single instance of RoomReservation into the database
	 * @param roomReservation
	 * @return the persisted record in a list
	 */
	public List<RoomReservation> persistRoomReservation(RoomReservation roomReservation) {
		List<RoomReservation> roomReservationList = new ArrayList<RoomReservation>();
		roomReservationList.add(roomReservation);
		return persistRoomReservation(roomReservationList);
	}	
	
	/**
	 * Insert RoomReservation into the database
	 * @param recordList A list of RoomReservation instances
	 * @return the persisted list of RoomReservations instances
	 */
	public List<RoomReservation> persistRoomReservation(List<RoomReservation> recordList) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			for (RoomReservation record : recordList) {
				em.persist(record);
			}
			et.commit();
		} finally {
			em.close();
		}
		return recordList;
	}
	
	/**
	 * Remove a RoomReservation from the database if it exists
	 * @param id
	 */
	public void removeRoomReservation(int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		RoomReservation roomReservation = em.find(RoomReservation.class, id);
		try {
			if (roomReservation != null) {
				et.begin();
				em.remove(roomReservation);
				et.commit();
			}
		} finally {
			em.close();
		}
	}
	
	/**
	 * Update an existing RoomReservation instance
	 * @param roomReservation
	 */
	public void updateRoomReservation(RoomReservation roomReservation) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		RoomReservation existingRoomReservation = em.find(RoomReservation.class, roomReservation.getId());
		try {
			if (existingRoomReservation != null) {
				et.begin();
				existingRoomReservation.clone(roomReservation);
				et.commit();
			}
		} finally {
			em.close();
		}
	}
	
	/**
	 * Return a map of Room Ids to a list of their reservations for a given Hotel
	 * @param hotel
	 */
	public Map<Integer, List<RoomReservation>> findRoomReservationsByHotel(Integer hotelId, String checkin, String checkout) {
		EntityManager em = getEntityManager();
		Query query = em.createNativeQuery(
			"SELECT res.Id, res.Reservation_Date, res.Room_Id, res.Reservation_Id, h.Id Hotel_Id " +
			"FROM Hotels h " +
			"INNER JOIN Rooms r ON h.Id = r.Hotel_Id " +
			"INNER JOIN Room_Reservations res ON r.Id = res.Room_Id " +
			"WHERE h.Id = :hotelId " +
			"AND res.Reservation_Date >= :checkin " +
			"AND res.Reservation_Date <= :checkout "
			, "RoomReservationMapping"
		);
		query.setParameter("hotelId", hotelId);
		query.setParameter("checkin", checkin);
		query.setParameter("checkout", checkout);
		
		// Map RoomReservations by their hotels
		Map<Integer, List<RoomReservation>> roomResMap
			= new HashMap<Integer, List<RoomReservation>>();
		
		List<RoomReservation> allRoomReservationsList = query.getResultList();
		
		for (RoomReservation res : allRoomReservationsList) {
			Integer roomId = res.getRoom().getId();
			if (!roomResMap.containsKey(roomId)) {
				roomResMap.put(roomId, new ArrayList<RoomReservation>());
			}
			roomResMap.get(roomId).add(res);
		}
		return roomResMap;
	}

}
