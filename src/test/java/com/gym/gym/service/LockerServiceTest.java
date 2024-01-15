package com.gym.gym.service;


import com.gym.gym.dto.LockerDto;
import com.gym.gym.exception.exceptions.EntityNotFoundException;
import com.gym.gym.mapper.LockerMapper;
import com.gym.gym.repository.LockerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

@ActiveProfiles("test")
class LockerServiceTest {


    @Autowired
    LockerRepository lockerRepository;
    @Autowired
    LockerService lockerService;

    @Test
    public void shouldReturnAllLockers() {

        List<LockerDto> lockers = lockerService.getAllLockers();

        assertEquals(20, lockers.size());

    }


    @Test
    public void shouldSaveLocker() {

        //given
        LockerDto lockerToSave = new LockerDto(21, false, false);
        lockerService.save(lockerToSave);
        //when
        LockerDto lockerByLockerId = LockerMapper.toDto(lockerRepository.findLockerByLockerId(lockerToSave.getLockerId()));

        //then
        assertEquals(lockerToSave, lockerByLockerId);
    }


    @Test
    public void shouldThrowExceptionWhenDeleteLockerWhichNoExist() {
        assertThrows(EntityNotFoundException.class, () -> lockerService.delete(1200l));
    }


    @Test
    public void shouldDeleteLocker() {
        //given
        LockerDto lockerToDelete = new LockerDto(22, false, false);
        lockerService.save(lockerToDelete);

        lockerService.delete(lockerToDelete.getLockerId());

        assertNull(lockerRepository.findLockerByLockerId(lockerToDelete.getLockerId()));

    }


}