package com.example.Smart.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "workspaces")
public class Workspaces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int floor;

    @Column(name = "seat_number")
    private String seat_number;

    private boolean is_occupied;
    private Long booked_by_user_id;
    private LocalDateTime booked_from;
    private LocalDateTime booked_to;

    public Workspaces() {
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

    public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
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
}
