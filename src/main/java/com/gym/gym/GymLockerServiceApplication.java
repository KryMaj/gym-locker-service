package com.gym.gym;

import com.gym.gym.dto.ClientDto;
import com.gym.gym.dto.ClientLockerDto;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;

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


            ClientDto client1 = new ClientDto(1, "jana", "janek", false, 36000);
            ClientDto client2 = new ClientDto(2, "jan1", "janek", false, 36000);
            ClientDto client3 = new ClientDto(3, "jan2a", "janek", false, 36000);
            ClientDto client4 = new ClientDto(4, "jan3", "janek", false, 36000);
            ClientDto client5 = new ClientDto(5, "jan3", "janek", false, 36000);
            clientRepository.save(ClientMapper.toEntity(client1));
            clientRepository.save(ClientMapper.toEntity(client2));
            clientRepository.save(ClientMapper.toEntity(client3));
            clientRepository.save(ClientMapper.toEntity(client4));
            clientRepository.save(ClientMapper.toEntity(client5));


            LockerDto locker1 = new LockerDto(1, false, true);
            LockerDto locker2 = new LockerDto(2, false, true);
            LockerDto locker3 = new LockerDto(3, false, true);
            LockerDto locker4 = new LockerDto(4, false, true);
            LockerDto locker5 = new LockerDto(5, false, true);
            LockerDto locker6 = new LockerDto(6, false, true);
            LockerDto locker7 = new LockerDto(7, false, true);
            LockerDto locker8 = new LockerDto(8, false, true);
            LockerDto locker9 = new LockerDto(9, false, true);
            LockerDto locker10 = new LockerDto(10, false, true);

            lockerRepository.save(LockerMapper.toEntity(locker1));
            lockerRepository.save(LockerMapper.toEntity(locker2));
            lockerRepository.save(LockerMapper.toEntity(locker3));
            lockerRepository.save(LockerMapper.toEntity(locker4));
            lockerRepository.save(LockerMapper.toEntity(locker5));
            lockerRepository.save(LockerMapper.toEntity(locker6));
            lockerRepository.save(LockerMapper.toEntity(locker7));
            lockerRepository.save(LockerMapper.toEntity(locker8));
            lockerRepository.save(LockerMapper.toEntity(locker9));
            lockerRepository.save(LockerMapper.toEntity(locker10));

            clientLockerService.save(1l);
            clientLockerService.save(2l);
            clientLockerService.save(3l);
            clientLockerService.save(4l);


//            ClientLockerDto clientLockerDto1 = new ClientLockerDto(1L, 1l, 1L, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusHours(1l)));
//            ClientLockerDto clientLockerDto2 = new ClientLockerDto(2L, 2l, 2l, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusHours(2l)));
//            ClientLockerDto clientLockerDto3 = new ClientLockerDto(3L, 3l, 3l, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusHours(3l)));
//            ClientLockerDto clientLockerDto4 = new ClientLockerDto(4L, 1l, 2l, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusHours(1l)));
//            ClientLockerDto clientLockerDto5 = new ClientLockerDto(5L, 1l, 2l, Timestamp.valueOf(LocalDateTime.now()));
//            ClientLockerDto clientLockerDto6 = new ClientLockerDto(6L, 2l, 1l, Timestamp.valueOf(LocalDateTime.now()));
//            ClientLockerDto clientLockerDto7 = new ClientLockerDto(7L, 3l, 3l, Timestamp.valueOf(LocalDateTime.now()));
//            ClientLockerDto clientLockerDto8 = new ClientLockerDto(8L, 4l, 4l, Timestamp.valueOf(LocalDateTime.now().minusMinutes(4)));
//
////
//            clientLockerRepository.save(clientLockerMapper.toEntity(clientLockerDto1));
//            clientLockerRepository.save(clientLockerMapper.toEntity(clientLockerDto2));
//            clientLockerRepository.save(clientLockerMapper.toEntity(clientLockerDto3));
//            clientLockerRepository.save(clientLockerMapper.toEntity(clientLockerDto4));
//            clientLockerRepository.save(clientLockerMapper.toEntity(clientLockerDto5));
//            clientLockerRepository.save(clientLockerMapper.toEntity(clientLockerDto6));
//            clientLockerRepository.save(clientLockerMapper.toEntity(clientLockerDto7));
//            clientLockerRepository.save(clientLockerMapper.toEntity(clientLockerDto8));


        };
    }


}
