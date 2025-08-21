package com.example.Smart.controller;

import com.example.Smart.model.MeetingRooms;
import com.example.Smart.services.MeetingRoomsServices;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/meetingrooms")
public class MeetingRoomsController {

    public final MeetingRoomsServices meetingRoomsServices;

    @Autowired
    public MeetingRoomsController(MeetingRoomsServices meetingRoomsServices) {
        this.meetingRoomsServices = meetingRoomsServices;
    }

    @GetMapping
    public List<MeetingRooms> getMeetingRooms(){
        return meetingRoomsServices.getMetingRooms();
    }


}
