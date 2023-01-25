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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GymLockerServiceApplication {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private ClientLockerRepository clientLockerRepository;
    @Autowired
    private ClientLockerMapper clientLockerMapper;

    @Autowired
    private ClientLockerService clientLockerService;

    public static void main(String[] args) {
        SpringApplication.run(GymLockerServiceApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo() {
        return (args) -> {


            ClientDto client1 = new ClientDto(1, "edward", "klimek", false, 0);
            ClientDto client2 = new ClientDto(2, "mariusz", "szubert", false, 0);
            ClientDto client3 = new ClientDto(3, "karol", "wojtyla", false, 0);
            ClientDto client4 = new ClientDto(4, "jan", "pawel", false, 0);
            ClientDto client5 = new ClientDto(5, "patryk", "janek", false, 0);
            ClientDto client6 = new ClientDto(6, "ewa", "szulc", true, 0);
            ClientDto client7 = new ClientDto(7, "ada", "gajos", true, 0);
            ClientDto client8 = new ClientDto(8, "karolina", "siekiewicz", true, 0);
            ClientDto client9 = new ClientDto(9, "natalia", "tuwim", true, 0);
            ClientDto client10 = new ClientDto(10, "sylwia", "kot", true, 0);
            clientRepository.save(ClientMapper.toEntity(client1));
            clientRepository.save(ClientMapper.toEntity(client2));
            clientRepository.save(ClientMapper.toEntity(client3));
            clientRepository.save(ClientMapper.toEntity(client4));
            clientRepository.save(ClientMapper.toEntity(client5));
            clientRepository.save(ClientMapper.toEntity(client6));
            clientRepository.save(ClientMapper.toEntity(client7));
            clientRepository.save(ClientMapper.toEntity(client8));
            clientRepository.save(ClientMapper.toEntity(client9));
            clientRepository.save(ClientMapper.toEntity(client10));

            for (int i = 1; i < 11; i++) {
                lockerRepository.save(LockerMapper.toEntity((new LockerDto(i, false, true))));
            }

            for (int i = 1; i < 11; i++) {
                lockerRepository.save(LockerMapper.toEntity((new LockerDto(10+i, true, true))));
            }


            clientLockerService.save(1l);
            clientLockerService.save(2l);
            clientLockerService.save(3l);
            clientLockerService.save(4l);
//

        };
    }


}
