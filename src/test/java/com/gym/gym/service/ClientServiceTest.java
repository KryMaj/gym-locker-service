package com.gym.gym.service;

import com.gym.gym.dto.ClientDto;
import com.gym.gym.exception.exceptions.EntityNotFoundException;
import com.gym.gym.mapper.ClientMapper;
import com.gym.gym.repository.ClientRepository;
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
class ClientServiceTest {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientService clientService;


    @Test
    @Transactional
    @Rollback
    public void shouldFindAllClients() {
        //given
        List<ClientDto> allClients = clientService.getAllClients();

        assertEquals(8, allClients.size());
    }


    @Test
    @Transactional
    @Rollback
    public void shouldSaveClient() {
        //given
        ClientDto clientToSave = new ClientDto(9, "Michał", "Kaminski", false, 0);
        clientService.save(clientToSave);

        //when
        ClientDto clientDto = ClientMapper.toDto(clientRepository.findClientByUserId(clientToSave.getUserId()));

        //then

        assertEquals(clientToSave, clientDto);
    }


    @Test
    @Transactional
    @Rollback
    public void shouldSaveClientWithNameSurname() {
        // given
        String name = "Ewa";
        String surname = "Szewczyk";
        boolean isAWoman = true;
        ClientDto saveClient = clientService.save(name, surname, isAWoman);

        //when
        ClientDto findClient = ClientMapper.toDto(clientRepository.findClientByUserId(saveClient.getUserId()));

        //then

        assertEquals(saveClient, findClient);
    }

    @Test
    @Transactional
    @Rollback
    public void shouldUpdateClient() {
        //given
        ClientDto clientToSave = new ClientDto(11, "Michał", "Kaminski", false, 0);
        clientService.save(clientToSave);
        ClientDto clientToUpdate = new ClientDto(11, "Michał", "Wiewiór", false, 0);
        clientService.update(clientToUpdate);
        //when
        ClientDto clientDto = ClientMapper.toDto(clientRepository.findClientByUserId(clientToUpdate.getUserId()));
        //then
        assertEquals(clientToUpdate, clientDto);
    }


    @Test
    @Transactional
    @Rollback
    public void shouldDeleteClient() {
        //given
        ClientDto clientToDelete = new ClientDto(11, "Michał", "Kaminski", false, 0);
        clientService.save(clientToDelete);
        clientService.delete(clientToDelete.getUserId());


        assertNull(clientRepository.findClientByUserId(clientToDelete.getUserId()));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldThrowExceptionWhenDeleteNonExistingClient() {


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            clientService.delete(11l);
        });

        assertEquals("Encja Client dla podanego id:11 nie istnieje!!!", exception.getMessage());
    }

    @Test
    @Transactional
    @Rollback
    public void shouldFindClient() {
        ClientDto clientToCheck = new ClientDto(11, "Michał", "Kaminski", false, 0);
        clientService.save(clientToCheck);

        boolean findClient = clientService.checkClientByUserId(clientToCheck.getUserId());

        assertTrue(findClient);


    }


}