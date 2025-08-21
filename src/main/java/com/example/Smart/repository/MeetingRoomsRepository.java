package com.example.Smart.repository;

import com.example.Smart.model.MeetingRooms;
import com.example.Smart.model.Workspaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MeetingRoomsRepository extends JpaRepository<MeetingRooms,Long> {
    @Query(value = "SELECT * FROM meeting_rooms WHERE number = :number", nativeQuery = true)
    Optional<MeetingRooms> findByNumberRoom(@Param("number") String  number);


    @Modifying
    @Query("UPDATE MeetingRooms w SET w.is_occupied = false, w.booked_by_user_id = null, w.booked_from = null, w.booked_to = null, w.reason_booking = null, w.conditioner = false " +
            "WHERE w.is_occupied = true AND w.booked_to < CURRENT_TIMESTAMP") // запрос передел
    int resetMeetingRooms();


    @Query("SELECT COUNT(w) > 0 FROM MeetingRooms w WHERE " +
            "w.booked_by_user_id = :userId AND " +
            "((w.booked_from <= :to) AND (w.booked_to >= :from))")
    boolean hasUserBookingInPeriodRooms(
            @Param("userId") Long userId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);

}
