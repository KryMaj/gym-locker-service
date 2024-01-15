package com.gym.gym.mapper;

import com.gym.gym.dto.ClientDto;
import com.gym.gym.entity.Client;

public interface ClientMapper {


    static ClientDto toDto(Client client) {
        return ClientDto.builder()
                .name(client.getName())
                .surname(client.getSurname())
                .isAWoman(client.isAWoman())
                .averageTime(client.getAverageTime())
                .userId(client.getUserId())
                .build();
    }

    static Client toEntity(ClientDto clientDto) {
        return Client.builder()
                .name(clientDto.getName())
                .surname(clientDto.getSurname())
                .isAWoman(clientDto.getIsAWoman())
                .averageTime(clientDto.getAverageTime())
                .userId(clientDto.getUserId())
                .build();
    }

}
