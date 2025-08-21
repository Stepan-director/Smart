package com.example.Smart.repository;

import com.example.Smart.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query(value = "select * from users where id = :id ", nativeQuery = true)
    Optional<Users> findById(Long id);

    @Modifying
    @Query("UPDATE Users u SET u.booked_meeting_room_id = null, " +
            "u.booked_meeting_room_from = null, " +
            "u.booked_meeting_room_to = null, " +
            "u.booked_meeting_room_reason = null " +
            "WHERE u.booked_meeting_room_to < CURRENT_TIMESTAMP")
    int resetExpiredUsersBookings(); // такой же только  для workspaces в таблицу users

    @Modifying
    @Query("UPDATE Users z SET z.booked_workspace_id = null, " +
            "z.booked_workspace_from = null, " +
            "z.booked_workspace_to = null " +
            "WHERE z.booked_workspace_to < CURRENT_TIMESTAMP")
    int resetExpiredWorkspaceBookings(); // такой же только  для workspaces в таблицу users
}
//"z.booked_workspace_reason = null "