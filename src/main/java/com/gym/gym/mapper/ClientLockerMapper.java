package com.gym.gym.mapper;

import com.gym.gym.dto.ClientLockerDto;
import com.gym.gym.entity.Client;
import com.gym.gym.entity.ClientLocker;
import com.gym.gym.entity.Locker;
import com.gym.gym.repository.ClientRepository;
import com.gym.gym.repository.LockerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ClientLockerMapper {

    private final ClientRepository clientRepository;
    private final LockerRepository lockerRepository;

    public ClientLockerDto toDto(ClientLocker clientLocker) {
        return ClientLockerDto.builder()
                .entry(clientLocker.getEntry())
                .idClient(clientLocker.getClient().getId())
                .idLocker(clientLocker.getLocker().getLockerId())
                .goHome(clientLocker.getGoHome())
                .clientLockerId(clientLocker.getId())
                .build();
    }

    public ClientLocker toEntity(ClientLockerDto clientLockerDto) {
        return ClientLocker.builder()
                .client(getClient(clientLockerDto.getIdClient()))
                .locker(getLocker(clientLockerDto.getIdLocker()))
                .entry(clientLockerDto.getEntry())
                .goHome(clientLockerDto.getGoHome())
                .build();
    }

    private Client getClient(Long id) {
        return clientRepository.findById(id).orElseThrow();
    }

    private Locker getLocker(Long id) {
        return lockerRepository.findById(id).orElseThrow();
    }
}
