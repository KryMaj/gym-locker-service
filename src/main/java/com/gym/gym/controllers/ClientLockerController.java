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

    @GetMapping("/women")
    public List<ClientLockerDto> getAllWomenClients() {
        return clientLockerService.checkClientIsNotAboutFinishDtoWoman();
    }

    @GetMapping("/women/id")
    public List<Long> getAllWomenId() {
        return clientLockerService.getLockerWomenIdWhichAreGoHome();
    }

    @GetMapping("/woman/id")
    public List<Long> getAllAvailableWomanId() {
        return clientLockerService.getAvailableLockerWomanId();
    }

    @GetMapping("/woman/justArrived")
    public List<Long> getJustArrivedWoman(){
        return clientLockerService.getLockerWomenIdWhichJustArrived();
    }

    @GetMapping("/men")
    public List<ClientLockerDto> getAllMenClients() {
        return clientLockerService.checkClientIsNotAboutFinishDtoMan();
    }


    @GetMapping("/men/id")
    public List<Long> getAllMenId() {
        return clientLockerService.getLockerManIdWhichAreGoHome();
    }
    @GetMapping("/man/id")
    public List<Long> getAllAvailableManId() {
        return clientLockerService.getAvailableLockerManId();
    }

    @GetMapping("/man/justArrived")
    public List<Long> getJustArrivedMan(){
//        return clientLockerService.getLockerMenIdWhichJustArrived();
      return clientLockerService.getOptimalIdManLocker();
    }

    @PostMapping
    public ClientLockerDto save(@RequestParam Long clientId) {
        return clientLockerService.save(clientId);
    }

    @PostMapping("/home")
    public ClientLockerDto goHome(@RequestParam Long clientId) {
        return clientLockerService.goHome(clientId);
    }
}


