package com.example.Smart.controller;

import com.example.Smart.model.Workspaces;
import com.example.Smart.services.WorkspacesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/workspaces")
public class WorkspacesController {

    private final WorkspacesServices workspacesServices;
    @Autowired
    public WorkspacesController(WorkspacesServices workspacesServices){
        this.workspacesServices = workspacesServices;
    }

    @GetMapping
    public List<Workspaces> getWorkspaces(){
        return workspacesServices.getWorkspaces();
    }




}
