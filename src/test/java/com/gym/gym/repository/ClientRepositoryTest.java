package com.gym.gym.repository;

import com.gym.gym.entity.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


class ClientRepositoryTest {

    ClientRepository clientRepository = mock(ClientRepository.class);


    @Test
    public void testFindClientByUserId() {

        Client client = new Client();
        client.setUserId(1L);
        client.setName("Jan");
        client.setSurname("Nowak");
        client.setAWoman(false);
        client.setAverageTime(30);


        when(clientRepository.findClientByUserId(1L)).thenReturn(client);


        Client result = clientRepository.findClientByUserId(1L);


        assertNotNull(result);
        assertEquals(result.getUserId(), 1L);
        assertEquals(result.getName(), "Jan");

    }

    @Test
    public void testDeleteByUserId() {

        clientRepository.deleteByUserId(1L);


        verify(clientRepository, times(1)).deleteByUserId(1L);
    }


}