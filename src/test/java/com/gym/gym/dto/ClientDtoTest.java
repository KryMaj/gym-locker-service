package com.gym.gym.dto;

import com.gym.gym.entity.Client;
import com.gym.gym.mapper.ClientMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientDtoTest {

    @Test
    void testFieldAccess() {
        ClientDto clientDto = new ClientDto();
        clientDto.setUserId(1);
        clientDto.setName("John");
        clientDto.setSurname("Doe");
        clientDto.setIsAWoman(false);
        clientDto.setAverageTime(30);

        assertEquals(1, clientDto.getUserId());
        assertEquals("John", clientDto.getName());
        assertEquals("Doe", clientDto.getSurname());
        assertFalse(clientDto.getIsAWoman());
        assertEquals(30, clientDto.getAverageTime());
    }

    @Test
    void testMapping() {
        Client clientEntity = new Client();
        clientEntity.setUserId(1L);
        clientEntity.setName("John");
        clientEntity.setSurname("Doe");
        clientEntity.setAWoman(false);
        clientEntity.setAverageTime(30);

        ClientDto clientDto = ClientMapper.toDto(clientEntity);

        assertEquals(1, clientDto.getUserId());
        assertEquals("John", clientDto.getName());
        assertEquals("Doe", clientDto.getSurname());
        assertFalse(clientDto.getIsAWoman());
        assertEquals(30, clientDto.getAverageTime());


        Client mappedEntity = ClientMapper.toEntity(clientDto);
        assertEquals(clientEntity, mappedEntity);
    }


}