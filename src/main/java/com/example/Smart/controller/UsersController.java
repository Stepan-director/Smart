package com.example.Smart.controller;

import com.example.Smart.model.Users;
import com.example.Smart.services.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {
    private final UsersServices usersServices;

    @Autowired
    public UsersController(UsersServices usersServices){
        this.usersServices = usersServices;
    }

    @GetMapping
    public List<Users> getUsers(){
        return usersServices.getUsers();
    }



}
