package com.gym.gym.controllers;

import com.gym.gym.dto.ClientLockerDto;
import com.gym.gym.service.ClientLockerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("cl")
@RestController
@RequiredArgsConstructor
public class ClientLockerController {

    private final ClientLockerService clientLockerService;


    @GetMapping
    public List<ClientLockerDto> getAllClients() {

        return clientLockerService.getACLDto();
    }


    @GetMapping("/man")
    public List<ClientLockerDto> getAllManClients() {
        return clientLockerService.getAllManClientLocker();
    }

    @GetMapping("/woman")
    public List<ClientLockerDto> getAllWomanClients() {
        return clientLockerService.getAllWomanClientLocker();
    }

    @GetMapping("/womans")
    public List<ClientLockerDto> getAllWomansClients() {
        return clientLockerService.checkClientIsNotAboutFinishDtoWoman();
    }

    @GetMapping("/mans")
    public List<ClientLockerDto> getAllMansClients() {
        return clientLockerService.checkClientIsNotAboutFinishDtoMan();
    }
//    @GetMapping("/{id}")
//    public int getAverageTimeClient(@PathVariable Long id) {
//
//        return clientLockerService.getAverageTime(id);
//    }

    @PostMapping
    public ClientLockerDto save(@RequestParam Long clientId) {
        return clientLockerService.save(clientId);
    }

    @PostMapping("/home")
    public ClientLockerDto goHome(@RequestParam Long clientId) {
        return clientLockerService.goHome(clientId);
    }
}


