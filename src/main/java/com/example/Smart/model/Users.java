package com.example.Smart.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "telegram_id")
    private String telegramId;
    private String first_name;
    private String last_name;
    private String patronymic;
    private String booked_workspace_id;
    private LocalDateTime booked_workspace_from;
    private LocalDateTime booked_workspace_to;
    public LocalDateTime booked_meeting_room_from;
    public LocalDateTime booked_meeting_room_to;
    public String booked_meeting_room_reason;
    @Column(name = "booked_meeting_room_id")
    public String booked_meeting_room_id;

    public Users() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBooked_workspace_id() {
        return booked_workspace_id;
    }

    public void setBooked_workspace_id(String booked_workspace_id) {
        this.booked_workspace_id = booked_workspace_id;
    }

    public LocalDateTime getBooked_workspace_from() {
        return booked_workspace_from;
    }

    public void setBooked_workspace_from(LocalDateTime booked_workspace_from) {
        this.booked_workspace_from = booked_workspace_from;
    }

    public LocalDateTime getBooked_workspace_to() {
        return booked_workspace_to;
    }

    public void setBooked_workspace_to(LocalDateTime booked_workspace_to) {
        this.booked_workspace_to = booked_workspace_to;
    }

    public LocalDateTime getBooked_meeting_room_from() {
        return booked_meeting_room_from;
    }

    public void setBooked_meeting_room_from(LocalDateTime booked_meeting_room_from) {
        this.booked_meeting_room_from = booked_meeting_room_from;
    }

    public LocalDateTime getBooked_meeting_room_to() {
        return booked_meeting_room_to;
    }

    public void setBooked_meeting_room_to(LocalDateTime booked_meeting_room_to) {
        this.booked_meeting_room_to = booked_meeting_room_to;
    }

    public String getBooked_meeting_room_reason() {
        return booked_meeting_room_reason;
    }

    public void setBooked_meeting_room_reason(String booked_meeting_room_reason) {
        this.booked_meeting_room_reason = booked_meeting_room_reason;
    }

    public String getBooked_meeting_room_id() {
        return booked_meeting_room_id;
    }

    public void setBooked_meeting_room_id(String booked_meeting_room_id) {
        this.booked_meeting_room_id = booked_meeting_room_id;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }
}
