package com.example.Smart.services;

import com.example.Smart.model.MeetingRooms;
import com.example.Smart.model.Workspaces;
import com.example.Smart.repository.MeetingRoomsRepository;
import com.example.Smart.repository.UsersRepository;
import jakarta.transaction.Transactional;
import jdk.jfr.Threshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetingRoomsServices {

    @Autowired
    private final MeetingRoomsRepository meetingRoomsRepository;
    @Autowired
    private final UsersRepository usersRepository2;
//    private final WeatherService weatherService;

    public MeetingRoomsServices(MeetingRoomsRepository meetingRoomsRepository, UsersRepository usersRepository2) {
        this.meetingRoomsRepository = meetingRoomsRepository;

        this.usersRepository2 = usersRepository2;
    }

    public List<MeetingRooms> getMetingRooms(){
        return meetingRoomsRepository.findAll();
    }

    public MeetingRooms findByNumberRoom(String number){
        return meetingRoomsRepository.findByNumberRoom(number).orElseThrow(() ->
                new RuntimeException("Переговорка № " + number + " не найдена"));
    }

    // бронь
    public MeetingRooms bookMeetingRooms(String number, Long userId, LocalDateTime from, LocalDateTime to, boolean conditioner, String reason_booking){
        MeetingRooms meetingRooms = meetingRoomsRepository.findByNumberRoom(number).orElseThrow(() -> new RuntimeException("Переговорка № " + number + " не найдена"));

        if(meetingRooms.isIs_occupied()){
            throw new RuntimeException("Место уже занято");
        }

//        проверка на две и больше брони нельзя
        if(meetingRoomsRepository.hasUserBookingInPeriodRooms(userId, from, to)){
            throw new RuntimeException("У вас уже есть бронь на это время!");
        }

//        // Получаем текущую температуру
//        double currentTemp = weatherService.getCurrentTemperature();
//
//        // температура > 17°C - включаем кондиционер
//        boolean needConditioner = currentTemp > 17;
        meetingRooms.setIs_occupied(true);
        meetingRooms.setBooked_to(to);
        meetingRooms.setBooked_from(from);
        meetingRooms.setConditioner(true);
        meetingRooms.setBooked_by_user_id(userId);  // причина ещё нужна


        return meetingRoomsRepository.save(meetingRooms);
    }

    // отмена брони
    public MeetingRooms cancelationMeetingRooms(String number, Long userId){
        MeetingRooms meetingRooms = meetingRoomsRepository.findByNumberRoom(number).orElseThrow(() -> new RuntimeException("Переговорка № " + number + " не найдена"));

        if(!meetingRooms.isIs_occupied() || !meetingRooms.getBooked_by_user_id().equals(userId)){
            throw  new RuntimeException("Вы не занимали переговорку № " + number);
        }



        meetingRooms.setBooked_to(null);
        meetingRooms.setBooked_from(null);
        meetingRooms.setConditioner(false);
        meetingRooms.setIs_occupied(false);
        meetingRooms.setBooked_by_user_id(null);

        return  meetingRoomsRepository.save(meetingRooms);
    }


    // продление
    public MeetingRooms extensionMeetingRooms(String number, Long userId, LocalDateTime to){
        MeetingRooms meetingRooms = meetingRoomsRepository.findByNumberRoom(number).orElseThrow(() -> new RuntimeException(""));

        meetingRooms.setBooked_to(to);
        meetingRooms.setBooked_by_user_id(userId);
        meetingRooms.setNumber(number);

        return meetingRoomsRepository.save(meetingRooms);
    }

    //кондишен





    // тут плюсик если ок
    // не ок
    @Transactional
    @Scheduled(fixedRate = 60000)
    public void resetMeetingRooms(){
        int expired = meetingRoomsRepository.resetMeetingRooms();
        int expiredUsers = usersRepository2.resetExpiredUsersBookings();
        System.out.println("Освобождено " + expired + " переговорок");
    }

}
