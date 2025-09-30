package com.example.Smart.controller;

import com.example.Smart.model.Users;
import com.example.Smart.services.MeetingRoomsServices;
import com.example.Smart.services.UsersServices;
import com.example.Smart.services.WorkspacesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final UsersServices usersServices;
    private final WorkspacesServices workspacesServices;
    private final MeetingRoomsServices meetingRoomsServices;

   @Autowired
    public BookingController(UsersServices usersServices, WorkspacesServices workspacesServices, MeetingRoomsServices meetingRoomsServices){
       this.usersServices = usersServices;
       this.workspacesServices = workspacesServices;
       this.meetingRoomsServices = meetingRoomsServices;
   }


   @GetMapping
    public String status(){
       return "!";
   }

    @PostMapping("/workspaces")
    public String bookWorkspace(
            @RequestParam String seatNumber,
            @RequestParam Long userId,
            @RequestParam String from,
            @RequestParam String to) {



        try {

            LocalDateTime startDate = LocalDateTime.parse(from);
            LocalDateTime endDate = LocalDateTime.parse(to);


            // бронирование
            workspacesServices.bookWorkspace(seatNumber, userId, startDate, endDate);


            usersServices.bookUsers(userId, seatNumber, startDate, endDate);

            return "Место " + seatNumber + " забронировано с " + from + " по " + to;
        } catch (DateTimeParseException e) {
            return "Неверный формат даты. Используйте YYYY-MM-DDTHH:MM:SS";
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelBooking(
            @RequestParam String seatNumber,
            @RequestParam Long userId) {

        try {
            workspacesServices.cancellation(seatNumber, userId);
            usersServices.clearUserBooking(userId);
            return ResponseEntity.ok("Бронирование места " + seatNumber + " отменено");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @PostMapping("/workspaces/extension")
    public String extensionWorkspaces( @RequestParam String seatNumber,
                                       @RequestParam Long userId,
                                       @RequestParam String to)
    {

       try {
           LocalDateTime endDate = LocalDateTime.parse(to);

           workspacesServices.extensionWorkspaces(seatNumber, userId, endDate);
           usersServices.extensionUsers(userId, seatNumber, endDate);

           return "Место " + seatNumber + " продлено до" + to;

       } catch (DateTimeParseException e) {
           return "Неверный формат даты. Используйте YYYY-MM-DDTHH:MM:SS";
       } catch (Exception e) {
           return "Ошибка: " + e.getMessage();
       }

    }

    //meeting
    @PostMapping("/meeting")
    public String  bookMeetingRooms( @RequestParam String number,
                                     @RequestParam Long userId,
                                     @RequestParam String from,
                                     @RequestParam String to,
                                     @RequestParam boolean conditioner,
                                     @RequestParam String reason_booking
    ){
       try {
           LocalDateTime startDate = LocalDateTime.parse(from);
           LocalDateTime endDate = LocalDateTime.parse(to);

           meetingRoomsServices.bookMeetingRooms(number, userId,startDate, endDate, conditioner, reason_booking);
           usersServices.bookUsersRooms(userId, number, startDate, endDate, reason_booking); // причина ещё нужна


           return "Переговорка " + number + " забронирована с " + from + " по " + to;
       }
       catch (DateTimeParseException e) {
           return "Неверный формат даты. Используйте YYYY-MM-DDTHH:MM:SS";
       } catch (Exception e) {
           return "Ошибка: " + e.getMessage();
       }
    }

    @PostMapping("/mcancel")
    public ResponseEntity<String> cancelBookingRoom(
            @RequestParam String number,
            @RequestParam Long userId) {

        try {
            meetingRoomsServices.cancelationMeetingRooms(number, userId);
            usersServices.clearUsersBookingRooms(userId);
            return ResponseEntity.ok("Бронирование переговороки " + number + " отменено");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/meeting/extension")
    public String extensionMeetingRooms( @RequestParam String number,
                                       @RequestParam Long userId,
                                       @RequestParam String to)
    {

        try {
            LocalDateTime endDate = LocalDateTime.parse(to);

            meetingRoomsServices.extensionMeetingRooms(number, userId, endDate);
            usersServices.extensionUsersRooms(userId, number, endDate);

            return "Место " + number + " продлено до" + to;

        } catch (DateTimeParseException e) {
            return "Неверный формат даты. Используйте YYYY-MM-DDTHH:MM:SS";
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }

    }

    @PostMapping("/telegramId")
    public void inputTelegramId(String first_name, String last_name, String telegramId) {
        usersServices.inputTelegramId(first_name, last_name, telegramId);
    }
    @PostMapping("/renameTelegramId")
    public void renameTelegramId(String first_name, String last_name, String telegramId){
       usersServices.renameTelegramId(first_name, last_name, telegramId);
    }
}