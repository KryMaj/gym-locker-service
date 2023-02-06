package com.gym.gym.repository;

import com.gym.gym.entity.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void shouldFindAllClient(){
        //given
        testEntityManager.persist(Client.builder()
                .name("jan")
                .surname("nowak")
                .averageTime(1000)
                .build());

        //when
        var clients = clientRepository.findAll();

        //then
        Assertions.assertEquals(1, clients.size());


    }



}