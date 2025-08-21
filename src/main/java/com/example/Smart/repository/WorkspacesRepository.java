package com.example.Smart.repository;

import com.example.Smart.model.Workspaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkspacesRepository extends JpaRepository<Workspaces, Long> {
    @Query(value = "SELECT * FROM workspaces WHERE seat_number = :seatNumber", nativeQuery = true)
    Optional<Workspaces> findBySeatNumber(@Param("seatNumber") String  seatNumber);

    @Query("SELECT COUNT(w) > 0 FROM Workspaces w WHERE " +
            "w.booked_by_user_id = :userId AND " +
            "((w.booked_from <= :to) AND (w.booked_to >= :from))")
    boolean hasUserBookingInPeriod(
            @Param("userId") Long userId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

//    @Query("SELECT w FROM Workspaces w WHERE w.is_occupied = true AND w.booked_to < CURRENT_TIMESTAMP")
    @Modifying
    @Query("UPDATE Workspaces w SET w.is_occupied = false, w.booked_by_user_id = null, w.booked_from = null, w.booked_to = null " +
            "WHERE w.is_occupied = true AND w.booked_to < CURRENT_TIMESTAMP")
    int resetExpiredBookings();



}