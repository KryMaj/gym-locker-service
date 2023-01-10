package com.gym.gym.controllers;

import com.gym.gym.dto.ClientDto;
import com.gym.gym.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("client")
@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;


    @GetMapping
    public List<ClientDto> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping
    public ClientDto save(@RequestBody ClientDto clientDto) {
        return clientService.save(clientDto);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.delete(id);
    }

    @DeleteMapping()
    public void deleteClient2(@RequestParam Long id) {
        clientService.delete(id);
    }
}


//poprawic dodawanie szafki, poprawci metode
