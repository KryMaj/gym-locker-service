package com.gym.gym;

import com.gym.gym.dto.ClientDto;
import com.gym.gym.dto.LockerDto;
import com.gym.gym.mapper.ClientLockerMapper;
import com.gym.gym.mapper.ClientMapper;
import com.gym.gym.mapper.LockerMapper;
import com.gym.gym.repository.ClientLockerRepository;
import com.gym.gym.repository.ClientRepository;
import com.gym.gym.repository.LockerRepository;
import com.gym.gym.service.ClientLockerService;
import com.gym.gym.service.ClientService;
import com.gym.gym.service.LockerService;
import org.hibernate.engine.spi.IdentifierValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class GymLockerServiceApplication {



    @Autowired
private LockerService lockerService;

    @Autowired
private ClientService clientService;
    @Autowired

    private ClientLockerService clientLockerService;



    public static void main(String[] args) {
        SpringApplication.run(GymLockerServiceApplication.class, args);
    }

@Profile("dev")
    @Bean
    public CommandLineRunner demo() {
        return (args) -> {


            ClientDto client1 = new ClientDto(1, "edward", "klimek", false, 0);
            ClientDto client2 = new ClientDto(2, "mariusz", "szubert", false, 0);
            clientService.save(client1);
            clientService.save(client2);


            clientService.save("edward", "klimek", false);
            clientService.save("edward", "klimek", false);
            clientService.save("edward", "klimek", false);
            clientService.save("edward", "klimek", false);

            for (int i = 1; i < 11; i++) {
//
                lockerService.save((new LockerDto(i, false, true)));
            }

            for (int i = 1; i < 11; i++) {
//
                lockerService.save((new LockerDto(i+10, true, true)));
            }

//
//            clientLockerService.save(1l);
//            clientLockerService.save(2l);
//            clientLockerService.save(3l);
//            clientLockerService.save(4l);


        };
    }


}
