package com.gym.gym.repository;

import com.gym.gym.entity.Locker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;




class LockerRepositoryTest {



    LockerRepository lockerRepository = mock(LockerRepository.class);



    @Test
    void shouldFindLockerByLockerId(){
        //given

        Locker locker = new Locker();
        locker.setLockerId(1L);

        //when
        when(lockerRepository.findLockerByLockerId(1L)).thenReturn(locker);

        Locker lockerByLockerId = lockerRepository.findLockerByLockerId(1L);

        //then
        assertEquals(lockerByLockerId, locker);
    }




}