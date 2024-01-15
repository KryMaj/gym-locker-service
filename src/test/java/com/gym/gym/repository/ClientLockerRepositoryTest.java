package com.gym.gym.repository;

import com.gym.gym.entity.ClientLocker;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientLockerRepositoryTest {

    ClientLockerRepository clientLockerRepository = mock(ClientLockerRepository.class);


    @Test
    public void shouldFindAllByGoHomeIsNull() {

        List<ClientLocker> clientLockers = Collections.EMPTY_LIST;

        when(clientLockerRepository.findAllByGoHomeIsNull()).thenReturn(clientLockers);

        List<ClientLocker> clientLockersFind = clientLockerRepository.findAllByGoHomeIsNull();


        assertEquals(clientLockers, clientLockersFind);
    }

}