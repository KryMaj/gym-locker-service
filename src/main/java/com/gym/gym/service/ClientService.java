package com.gym.gym.service;

import com.gym.gym.dto.ClientDto;
import com.gym.gym.entity.Client;
import com.gym.gym.exception.exceptions.EntityNotFoundException;
import com.gym.gym.mapper.ClientMapper;
import com.gym.gym.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    public List<Client> getAllClient() {
        return new ArrayList<>(clientRepository.findAll());
    }




    public ClientDto save(ClientDto clientDto) {
        return ClientMapper.toDto(clientRepository.save(ClientMapper.toEntity(clientDto)));
    }



    public ClientDto save(String name, String surname, boolean isAWoman) {
        ClientDto clientDto = new ClientDto(setClientId() ,name, surname, isAWoman, 0);

        return ClientMapper.toDto(clientRepository.save(ClientMapper.toEntity(clientDto)));
    }

//    public ClientDto update(ClientDto clientUpdate) {
//
//        if (clientRepository.findById(clientUpdate.getUserId()).isEmpty()) {
//            throw new EntityNotFoundException("Client", clientUpdate.getUserId());
//        } else {
//
//            Client clientByUserId = clientRepository.findClientByUserId(clientUpdate.getUserId());
//            clientByUserId.setName(clientUpdate.getName());
//            clientByUserId.setSurname(clientUpdate.getSurname());
//            clientByUserId.setAWoman(clientUpdate.getIsAWoman());
//
//            clientRepository.save(clientByUserId);
//            return clientUpdate;
//        }
//    }
    public ClientDto update(ClientDto clientUpdate) {

//        if (!checkClientByUserId(clientUpdate.getUserId())) {
//            throw new EntityNotFoundException("Client", clientUpdate.getUserId());
//        } else {

            Client clientByUserId = clientRepository.findClientByUserId(clientUpdate.getUserId());
            clientByUserId.setName(clientUpdate.getName());
            clientByUserId.setSurname(clientUpdate.getSurname());
            clientByUserId.setAWoman(clientUpdate.getIsAWoman());

            clientRepository.save(clientByUserId);
            return clientUpdate;
//        }
    }





    public void delete(Long id) {
        if (!checkClientByUserId(id)){
            throw new EntityNotFoundException("Client", id);
        }
        clientRepository.deleteByUserId(id);
    }



    public boolean checkClientByUserId(Long userId) {
        return clientRepository.findAll()
                .stream()
                .anyMatch(x -> x.getUserId().equals(userId));
    }



    public ClientDto getClientById(Long userId) {

        return  ClientMapper.toDto(clientRepository.findClientByUserId(userId));
    }

    private Long setClientId(){

       Long clientId = clientRepository.findAll().stream()
               .map(Client::getId)
               .sorted(Comparator.reverseOrder())
               .findFirst()
               .orElse(0l);


        if (clientId==0){
            return 1l;
        }
        else {
            return clientId+1;
        }
    }


}
