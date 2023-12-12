package com.gym.gym.controllers;

import com.gym.gym.dto.ClientDto;
import com.gym.gym.entity.Client;
import com.gym.gym.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
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


    @GetMapping("/x")
    public List<Client> getAllClient() {
        return clientService.getAllClient();
    }



    @PostMapping
    public ClientDto save(@RequestBody ClientDto clientDto) {
        return clientService.save(clientDto);
    }

    @PutMapping
    public ClientDto update(@RequestBody ClientDto clientDto){
        return clientService.update(clientDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.delete(id);
    }




}


