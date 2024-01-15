package com.gym.gym.service;

import com.gym.gym.dto.ClientLockerDto;
import com.gym.gym.exception.exceptions.EntityNotFoundException;
import com.gym.gym.repository.ClientLockerRepository;
import com.gym.gym.repository.ClientRepository;
import com.gym.gym.repository.LockerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class ClientLockerServiceTest {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientLockerRepository clientLockerRepository;

    @Autowired
    LockerRepository lockerRepository;

    @Autowired
    ClientLockerService clientLockerService;

    @Test
    @Transactional
    @Rollback
    public void shouldFindAllClientLocker() {
        //given
        List<ClientLockerDto> clientLockers = clientLockerService.getACLDto();
        //then
        assertEquals(4, clientLockers.size());
    }

    @Test
    @Transactional
    @Rollback
    public void shouldSaveClientLocker() {
        //given
        Long clientId = 5l;
        clientLockerService.save(clientId);
        //when
        List<ClientLockerDto> clientLockers = clientLockerService.getACLDto();
        //then
        assertEquals(5, clientLockers.size());
    }


    @Test
    @Transactional
    @Rollback
    public void shouldChangeValueOfGoHome() {
        //given
        Long clientId = 8l;
        ClientLockerDto clientLockerDto = clientLockerService.goHome(clientId);
        //then
        assertNotNull(clientLockerDto.getGoHome());
    }

    @Test
    @Transactional
    @Rollback
    public void shouldThrowExceptionWhenClientLockerNonExist() {
        //given
        Long clientId = 4l;

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            clientLockerService.goHome(clientId);
        });

        assertEquals("Encja nie istnieje", exception.getMessage());
    }
}