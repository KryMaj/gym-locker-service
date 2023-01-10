package com.gym.gym.service;

import com.gym.gym.dto.ClientDto;
import com.gym.gym.entity.Client;
import com.gym.gym.mapper.ClientMapper;
import com.gym.gym.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<ClientDto> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(ClientMapper::toDto)
                .collect(Collectors.toList());
    }

    public ClientDto save(ClientDto clientDto) {
        return ClientMapper.toDto(clientRepository.save(ClientMapper.toEntity(clientDto)));
    }

    public ClientDto update(ClientDto clientUpdate) {
        if (checkClientByUserId(clientUpdate.getUserId())) {

            Client clientByUserId = clientRepository.findClientByUserId(clientUpdate.getUserId());
            clientByUserId.setName(clientUpdate.getName());
            clientByUserId.setSurname(clientUpdate.getSurname());
            clientByUserId.setAWoman(clientUpdate.isAWoman());

            clientRepository.save(clientByUserId);
            return clientUpdate;
        }
        return null;
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    private boolean checkClientByUserId(Long userId) {
        return clientRepository.findAll()
                .stream()
                .anyMatch(x -> x.getUserId().equals(userId));
    }
}
