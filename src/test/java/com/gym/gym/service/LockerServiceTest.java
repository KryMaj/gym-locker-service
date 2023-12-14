package com.gym.gym.service;


import com.gym.gym.dto.LockerDto;
import com.gym.gym.entity.Locker;
import com.gym.gym.exception.exceptions.EntityNotFoundException;
import com.gym.gym.mapper.LockerMapper;
import com.gym.gym.repository.LockerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class LockerServiceTest {

    @Mock
    private LockerRepository lockerRepository;

    @InjectMocks
    private LockerService lockerService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllLockers_shouldReturnListOfLockers() {
        // Given
        when(lockerRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        var result = lockerService.getAllLockers();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(lockerRepository).findAll();
    }

    @Test
    void save_shouldReturnSavedLockerDto() {
        // Given
        LockerDto inputLockerDto = new LockerDto();
        Locker savedEntity = LockerMapper.toEntity(inputLockerDto);
        when(lockerRepository.save(any())).thenReturn(savedEntity);

        // When
        var result = lockerService.save(inputLockerDto);

        // Then
        assertNotNull(result);
        assertEquals(inputLockerDto, LockerMapper.toDto(savedEntity));

        // Optionally, you can verify that the repository method was called
        verify(lockerRepository).save(any());
    }


    @Test
    void delete_nonExistingLocker_shouldThrowEntityNotFoundException() {
        // Given
        Long nonExistingLockerId = 2L;
        when(lockerRepository.findById(nonExistingLockerId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> lockerService.delete(nonExistingLockerId));

        // Optionally, you can verify that the repository method was not called
        verifyNoInteractions(lockerRepository);
    }


}