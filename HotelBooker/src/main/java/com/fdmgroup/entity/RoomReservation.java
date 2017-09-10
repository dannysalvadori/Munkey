package com.fdmgroup.entity;

import java.util.Date;
import javax.persistence.*;

@SqlResultSetMapping(
	name="RoomReservationMapping",
	classes={
		@ConstructorResult(
			targetClass=RoomReservation.class,
            columns={
                @ColumnResult(name="Id", type=Integer.class),
                @ColumnResult(name="Reservation_Date", type=Date.class),
                @ColumnResult(name="Room_Id", type=Integer.class),
                @ColumnResult(name="Reservation_Id", type=Integer.class),
                @ColumnResult(name="Hotel_Id", type=Integer.class)
            }
		)
    }
)

@Entity
@Table(name = "Room_Reservations")
public class RoomReservation {

	@Id
	@Column
	private Integer id;
	
	@ManyToOne(optional=false, targetEntity=Room.class)
	@JoinColumn(name = "room_id", nullable=false, updatable=false)
	private Room room;
	
	@ManyToOne(optional=false, targetEntity=Reservation.class) 
	@JoinColumn(name="reservation_id", nullable=false, updatable=false)
	private Reservation reservation;
	
	@Column(name="reservation_date", nullable = false)
	private Date reservationDate;

	@Transient
	private Integer hotelId;
	
	public RoomReservation() {}
	
	public RoomReservation(
		Integer id,
		Date reservationDate,
		Integer roomId,
		Integer reservationId,
		Integer hotelId
	) {
		this.id = id;
		this.reservationDate = reservationDate;
		this.room = new Room(roomId);
		this.reservation = new Reservation(reservationId);
		this.hotelId = hotelId;
	}
	
	// Copies non-Id column values from a given RoomReservation instance
	public void clone(RoomReservation roomReservation) {
		this.room = roomReservation.getRoom();
		this.reservation = roomReservation.getReservation();
		this.reservationDate = roomReservation.getDate();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Date getDate() {
		return reservationDate;
	}

	public void setDate(Date date) {
		this.reservationDate = date;
	}

	public Integer getHotelId() {
		return hotelId;
	}

	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}

}
