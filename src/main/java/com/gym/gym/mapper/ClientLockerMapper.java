package com.gym.gym.mapper;

import com.gym.gym.dto.ClientLockerDto;
import com.gym.gym.entity.Client;
import com.gym.gym.entity.ClientLocker;
import com.gym.gym.entity.Locker;
import com.gym.gym.repository.ClientRepository;
import com.gym.gym.repository.LockerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



public interface ClientLockerMapper {


    static ClientLockerDto toDto(ClientLocker clientLocker) {
        return ClientLockerDto.builder()
                .entry(clientLocker.getEntry())
                .idClient(clientLocker.getClient().getId())
                .idLocker(clientLocker.getLocker().getLockerId())
                .goHome(clientLocker.getGoHome())
                .clientLockerId(clientLocker.getClientLockerId())
                .build();
    }

    static ClientLocker toEntity(ClientLockerDto clientLockerDto,
                                 LockerRepository lockerRepository,
                                 ClientRepository clientRepository) {
        return ClientLocker.builder()
                .client(clientRepository.findById(clientLockerDto.getIdClient()).orElseThrow())
                .locker(lockerRepository.findById(clientLockerDto.getIdLocker()).orElseThrow())
                .entry(clientLockerDto.getEntry())
                .goHome(clientLockerDto.getGoHome())
                .clientLockerId(clientLockerDto.getClientLockerId())
                .build();
    }


}
