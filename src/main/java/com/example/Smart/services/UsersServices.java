package com.example.Smart.services;

import com.example.Smart.model.Users;
import com.example.Smart.repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsersServices {
    @Autowired
    private final UsersRepository usersRepository;

    public UsersServices(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public List<Users> getUsers(){
        return usersRepository.findAll();
    }

    public Users findById(Long userId){
        return usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public Users bookUsers(Long userId, String seatNumber, LocalDateTime from, LocalDateTime to){
        Users users = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException
                ("Вы уже бронировали место: " + seatNumber + " отмените, чтобы забронировать снова"));



        users.setBooked_workspace_id(seatNumber);
        users.setBooked_workspace_from(from);
        users.setBooked_workspace_to(to);

        return usersRepository.save(users);
    }

    public Users clearUserBooking(Long userId){
    Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        user.setBooked_workspace_id(null);
        user.setBooked_workspace_from(null);
        user.setBooked_workspace_to(null);

    return usersRepository.save(user);
    }

    public Users extensionUsers(Long userId, String seatNumber, LocalDateTime to){

        Users users = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException
                (""));

        users.setBooked_workspace_id(seatNumber);

        users.setBooked_workspace_to(to);

        return usersRepository.save(users);
    }







    // продление
    public Users extensionUsersRooms(Long userId, String number, LocalDateTime to){

        Users users3 = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException
                (""));

        users3.setBooked_meeting_room_id(number);

        users3.setBooked_meeting_room_to(to);

        return usersRepository.save(users3);
    }

    // бронь
    public Users bookUsersRooms(Long userId, String number, LocalDateTime from, LocalDateTime to, String reason){
        Users users = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException
                ("Вы уже бронировали место: " + number + " отмените, чтобы забронировать снова"));

        users.setBooked_meeting_room_id(number);
        users.setBooked_meeting_room_from(from);
        users.setBooked_meeting_room_to(to);
        users.setBooked_meeting_room_reason(reason);
        return usersRepository.save(users);
    }


    // отмена переделать
    public Users clearUsersBookingRooms(Long userId){
        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден"));


        user.setBooked_meeting_room_id(null);
        user.setBooked_meeting_room_to(null);
        user.setBooked_meeting_room_from(null);
        user.setBooked_meeting_room_reason(null);

        return usersRepository.save(user);
    }



}
