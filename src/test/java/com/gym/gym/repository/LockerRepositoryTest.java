package com.gym.gym.repository;

import com.gym.gym.entity.Locker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class LockerRepositoryTest {

    @Autowired
    LockerRepository lockerRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void shouldFindAllLocker(){
        //given
        testEntityManager.persist(Locker.builder()
                        .lockerId(1L)
                        .isAWomenLocker(true)
                        .isAvailable(true)
                .build());

        //when
        var lockers = lockerRepository.findAll();

        //then
        Assertions.assertEquals(1, lockers.size());
    }


    @Test
    void shouldFindLockerByLockerId(){
        //given
        testEntityManager.persist(Locker.builder()
                .lockerId(12L)
                .isAWomenLocker(true)
                .isAvailable(true)
                .build());

        //when
        var locker = lockerRepository.findLockerByLockerId(12L);

        //then
        Assertions.assertEquals(12, locker.getLockerId());
    }

}