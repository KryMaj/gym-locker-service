package com.gym.gym.dto;

import com.gym.gym.entity.Locker;
import com.gym.gym.mapper.LockerMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LockerDtoTest {


    @Test
    void testFieldAccess() {
        LockerDto lockerDto = new LockerDto();
        lockerDto.setLockerId(1);
        lockerDto.setAWomenLocker(false);
        lockerDto.setAvailable(true);


        assertEquals(1,  lockerDto.getLockerId());
        assertFalse(lockerDto.isAWomenLocker());
        assertTrue(lockerDto.isAvailable());
    }

    @Test
    void testBuilder() {
        // Given
        LockerDto dto = LockerDto.builder()
                .lockerId(1L)
                .isAvailable(true)
                .isAWomenLocker(false)
                .build();

        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getLockerId());
        assertTrue(dto.isAvailable());
        assertFalse(dto.isAWomenLocker());

    }

    @Test
    void testMapping(){

        Locker locker = new Locker();
        locker.setLockerId(1L);
        locker.setAWomenLocker(false);
        locker.setAvailable(true);

        LockerDto dto = LockerMapper.toDto(locker);


        assertThat(dto).isNotNull();
        assertThat(dto.getLockerId()).isEqualTo(1L);
        assertThat(dto.isAWomenLocker()).isEqualTo(false);
        assertThat(dto.isAvailable()).isEqualTo(true);

        Locker mappedEntity = LockerMapper.toEntity(dto);

        assertEquals(locker, mappedEntity);



    }


}