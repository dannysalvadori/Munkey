package com.fdmgroup.service;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.fdmgroup.entity.Hotel;
import com.fdmgroup.entity.Room;

public class RoomService {

	private EntityManagerFactory emf;

	public RoomService(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	/**
	 * Fetch Room from database by Id
	 * @param id
	 * @return Room identified by the given id
	 */
	public Room findRoom(int id) {
		Room room;
		EntityManager em = getEntityManager();
		try {
			room = em.find(Room.class, id);
		} finally {
			em.close();
		}
		return room;
	}
	
	/**
	 * Fetch all Rooms for the specified Hotel
	 * @param Hotel
	 * @return List of Rooms that belong to the given Hotel
	 */
	public Map<Integer, Room> findRoomsByHotel(Hotel hotel) {
		EntityManager em = getEntityManager();
		TypedQuery<Room> query = em.createQuery(
				"SELECT r from Room r"
				+ " WHERE Hotel_Id = '" + hotel.getId() + "'", Room.class);
		Map<Integer, Room> roomMap = new HashMap<Integer, Room>();
		for (Room r : query.getResultList()) {
			roomMap.put(r.getId(), r);
		}
		return roomMap;
	}
	
	/**
	 * Insert Room into the database
	 * @param room
	 * @return the persisted Room instance
	 */
	public Room persistRoom(Room room) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(room);
			et.commit();
		} finally {
			em.close();
		}
		return room;
	}
	
	/**
	 * Remove a Room from the database if it exists
	 * @param id
	 */
	public void removeRoom(int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		Room room = em.find(Room.class, id);
		try {
			if (room != null) {
				et.begin();
				em.remove(room);
				et.commit();
			}
		} finally {
			em.close();
		}
	}
	
	/**
	 * Update an existing Room instance
	 * @param user
	 */
	public void updateRoom(Room room) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		Room existingReservation = em.find(Room.class, room.getId());
		try {
			if (existingReservation != null) {
				et.begin();
				existingReservation.clone(room);
				et.commit();
			}
		} finally {
			em.close();
		}
	}

}
