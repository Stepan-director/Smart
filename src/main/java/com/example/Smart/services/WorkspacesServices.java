package com.example.Smart.services;

import com.example.Smart.model.Workspaces;
import com.example.Smart.repository.UsersRepository;
import com.example.Smart.repository.WorkspacesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkspacesServices {

    @Autowired
    private final WorkspacesRepository workspacesRepository;
    @Autowired
    private final UsersRepository usersRepository2;

    public WorkspacesServices(WorkspacesRepository workspacesRepository, UsersRepository usersRepository2){
        this.workspacesRepository = workspacesRepository;
        this.usersRepository2 = usersRepository2;
    }

    public List<Workspaces> getWorkspaces(){
        return workspacesRepository.findAll();
    }

    public Workspaces findBySeatNumber(String  seatNumber){
        return workspacesRepository.findBySeatNumber(seatNumber).orElseThrow(() -> new RuntimeException("Место не найдено"));
    }

    public Workspaces bookWorkspace(String seatNumber, Long userId, LocalDateTime from, LocalDateTime to){
        Workspaces workspaces = workspacesRepository.findBySeatNumber(seatNumber)
                .orElseThrow(() -> new RuntimeException("Место " + seatNumber + " не найдено"));

        if(workspaces.isIs_occupied()){
            throw new RuntimeException("Место уже занято");
        }

        workspaces.setIs_occupied(true);
        workspaces.setBooked_by_user_id(userId);
        workspaces.setBooked_from(from);
        workspaces.setBooked_to(to);

        if(workspacesRepository.hasUserBookingInPeriod(userId, from, to)){
            throw new RuntimeException("У вас уже есть бронь на это время!");
        }


        return  workspacesRepository.save(workspaces);
    }

    public Workspaces cancellation(String seatNumber, Long userId) {

        Workspaces workspace = workspacesRepository.findBySeatNumber(seatNumber)
                .orElseThrow(() -> new RuntimeException("Место" + seatNumber + "не найдено"));

        if(!workspace.isIs_occupied() || !workspace.getBooked_by_user_id().equals(userId)){
            throw new RuntimeException("Вы не занимали это место");
        }

        workspace.setIs_occupied(false);
        workspace.setBooked_by_user_id(null);
        workspace.setBooked_from(null);
        workspace.setBooked_to(null);

        return workspacesRepository.save(workspace);
    }

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void resetExpiredBookings(){
        int expired = workspacesRepository.resetExpiredBookings();
        int expiredUsers = usersRepository2.resetExpiredWorkspaceBookings();

        System.out.println("Освобождено " + expired + " мест");
    }

    public Workspaces extensionWorkspaces(String seatNumber, Long userId, LocalDateTime to){
        Workspaces workspaces = workspacesRepository.findBySeatNumber(seatNumber).orElseThrow(() -> new RuntimeException(""));


        workspaces.setIs_occupied(true);
        workspaces.setBooked_by_user_id(userId);

        workspaces.setBooked_to(to);


        return workspacesRepository.save(workspaces);
    }
}
