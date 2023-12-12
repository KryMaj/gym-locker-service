package com.gym.gym.controllers;

import com.gym.gym.dto.ClientDto;
import com.gym.gym.dto.LockerDto;
import com.gym.gym.entity.Client;
import com.gym.gym.service.ClientLockerService;
import com.gym.gym.service.ClientService;
import com.gym.gym.service.LockerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping
@RequiredArgsConstructor
@Controller
public class ThymeleafController {

    private final ClientService clientService;
    private final ClientLockerService clientLockerService;

    @GetMapping("/home")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }

    @GetMapping("/user")
    public String user(Model model) {
        return "user";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }


    @GetMapping("/logout")
    public String logout(Model model) {
        return "home";
    }


    @GetMapping("/lockers")
    public String lockers(Model model) {return "lockers";}


    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("clientDto", new ClientDto());
        clientService.getAllClients();
        return "users";
    }



    @PostMapping("/users")
    public String usersPost(@ModelAttribute("clientDto") ClientDto clientDto, Model model) {
        try {

            ClientDto existingClient = clientService.getClientById(clientDto.getUserId());

            if (existingClient == null) {

                model.addAttribute("errorMessage", "Klient o podanym ID nie istnieje");
            } else {
                clientService.update(clientDto);
                model.addAttribute("successMessage", "Klient został zaktualizowany pomyślnie");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Błąd podczas aktualizacji klienta");
        }

        return "users";
    }

    @DeleteMapping("/delete/{id}")
    public String usersDelete(@PathVariable Long id, Model model) {
        clientService.delete(id);
        return "admin";
    }



    @GetMapping("/save")
    public String copy(   @ModelAttribute("clientDto") ClientDto clientDto,Model model){
        return "save";
    }

@PostMapping("/save")
    public String save(@ModelAttribute("clientDto") ClientDto clientDto, Model model){

        clientService.save(clientDto.getName(),clientDto.getSurname(), clientDto.getIsAWoman());
    return "redirect:/users";
    }

    @GetMapping("/clients")
    public String clients(Model model) {return "clients";}




    @PostMapping("/clients")
    public String register(@RequestParam("idClient") Long idClient, Model model) {
        if (!clientLockerService.existsById(idClient)) {
            model.addAttribute("error", "User with ID " + idClient + " does not exist.");
            return "clients";
        } else if (clientLockerService.stillTraining(idClient)) {
            model.addAttribute("error2", "User with ID " + idClient + " still training");

            return "clients";
        }

        model.addAttribute("info", "User with ID " + idClient + " save");
        clientLockerService.save(idClient);
        return "clients";
    }

    @PostMapping("/goHome")
    public String goHome(@RequestParam("idClient") Long idClient, Model model){
        if (!clientLockerService.existsById(idClient)) {
            model.addAttribute("error3", "User with ID " + idClient + " does not exist.");

            return "clients";
        } else if (!clientLockerService.stillTraining(idClient)) {
            model.addAttribute("error4", "User with ID " + idClient + " is not available");

            return "clients";
        }


        model.addAttribute("info2", "User with ID " + idClient + " exit");
        clientLockerService.goHome(idClient);
        return "clients";
    }



    @GetMapping("/update")
    public String updateGet(   @ModelAttribute("clientDto") ClientDto clientDto,Model model){
        return "update";
    }

    @PostMapping("/update")
    public String updatePost(@ModelAttribute("clientDto") ClientDto clientDto, Model model){

        if (!clientService.checkClientByUserId(clientDto.getUserId())) {
            model.addAttribute("error3", "User with ID " + clientDto.getUserId() + " does not exist.");

            return "update";
        }

        clientService.update(clientDto);
        return "users";
    }




}
