package com.gym.gym.dto;

import com.gym.gym.entity.Client;
import com.gym.gym.entity.ClientLocker;
import com.gym.gym.entity.Locker;
import com.gym.gym.mapper.ClientLockerMapper;
import com.gym.gym.repository.ClientRepository;
import com.gym.gym.repository.LockerRepository;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ClientLockerDtoTest {



    @Test
    void testEqualsAndHashCode() {
        // Given
        ClientLockerDto dto1 = new ClientLockerDto(1L, 2L, 3L, Timestamp.valueOf("2023-01-01 12:00:00"));
        ClientLockerDto dto2 = new ClientLockerDto(1L, 2L, 3L, Timestamp.valueOf("2023-01-01 12:00:00"));
        ClientLockerDto dto3 = new ClientLockerDto(4L, 5L, 6L, Timestamp.valueOf("2023-01-01 12:00:00"));

        // Then
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testBuilder() {
        // Given
        ClientLockerDto dto = ClientLockerDto.builder()
                .clientLockerId(1L)
                .idClient(2L)
                .idLocker(3L)
                .entry(Timestamp.valueOf("2023-01-01 12:00:00"))
                .goHome(Timestamp.valueOf("2023-01-01 18:00:00"))
                .build();

        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getClientLockerId());
        assertEquals(2L, dto.getIdClient());
        assertEquals(3L, dto.getIdLocker());
        assertEquals(Timestamp.valueOf("2023-01-01 12:00:00"), dto.getEntry());
        assertEquals(Timestamp.valueOf("2023-01-01 18:00:00"), dto.getGoHome());
    }



    @Test
    void testMapping() {
        //Mock
        Client client = mock(Client.class);
        Locker locker = mock(Locker.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        LockerRepository lockerRepository = mock(LockerRepository.class);

        // Given
        ClientLocker clientLocker = new ClientLocker();
        clientLocker.setClientLockerId(1L);
        clientLocker.setEntry(Timestamp.valueOf("2023-01-01 12:00:00"));
        clientLocker.setGoHome(Timestamp.valueOf("2023-01-01 18:00:00"));
        clientLocker.setClient(client);
        clientLocker.setLocker(locker);


            when(client.getId()).thenReturn(1L);
            when(locker.getLockerId()).thenReturn(3L);

            when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
            when(lockerRepository.findById(locker.getLockerId())).thenReturn(Optional.of(locker));

        // When
        ClientLockerDto dto = ClientLockerMapper.toDto(clientLocker);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getClientLockerId()).isEqualTo(1L);
        assertThat(dto.getIdClient()).isEqualTo(1L);
        assertThat(dto.getIdLocker()).isEqualTo(3L);
        assertThat(dto.getEntry()).isEqualTo(Timestamp.valueOf("2023-01-01 12:00:00"));
        assertThat(dto.getGoHome()).isEqualTo(Timestamp.valueOf("2023-01-01 18:00:00"));

        ClientLocker mappedEntity = ClientLockerMapper.toEntity(dto, lockerRepository, clientRepository);


assertEquals(clientLocker,mappedEntity);

    }




}