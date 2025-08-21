package com.example.Smart.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_rooms")
public class MeetingRooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int floor;

    @Column(name = "number")
    private String number;

    private boolean is_occupied;

    private Long booked_by_user_id;
    private LocalDateTime booked_from;
    private LocalDateTime booked_to;

    @Column(name = "reason_booking")
    private String reason_booking;

    private boolean conditioner;

    public MeetingRooms() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isIs_occupied() {
        return is_occupied;
    }

    public void setIs_occupied(boolean is_occupied) {
        this.is_occupied = is_occupied;
    }

    public Long getBooked_by_user_id() {
        return booked_by_user_id;
    }

    public void setBooked_by_user_id(Long booked_by_user_id) {
        this.booked_by_user_id = booked_by_user_id;
    }

    public LocalDateTime getBooked_from() {
        return booked_from;
    }

    public void setBooked_from(LocalDateTime booked_from) {
        this.booked_from = booked_from;
    }

    public LocalDateTime getBooked_to() {
        return booked_to;
    }

    public void setBooked_to(LocalDateTime booked_to) {
        this.booked_to = booked_to;
    }

    public String getReason_booking() {
        return reason_booking;
    }

    public void setReason_booking(String reason_booking) {
        this.reason_booking = reason_booking;
    }

    public boolean isConditioner() {
        return conditioner;
    }

    public void setConditioner(boolean conditioner) {
        this.conditioner = conditioner;
    }
}

