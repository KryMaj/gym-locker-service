package com.gym.gym.service;

import com.gym.gym.dto.LockerDto;
import com.gym.gym.exception.exceptions.EntityNotFoundException;
import com.gym.gym.mapper.LockerMapper;
import com.gym.gym.repository.LockerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LockerService {


    private final LockerRepository lockerRepository;


    public List<LockerDto> getAllLockers() {
        return lockerRepository.findAll()
                .stream()
                .map(LockerMapper::toDto)
                .collect(Collectors.toList());
    }

    public LockerDto save(LockerDto lockerDto) {
        return LockerMapper.toDto(lockerRepository.save(LockerMapper.toEntity(lockerDto)));
    }

    public void delete(Long id) {

        if (lockerRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Locker", id);
        }
        lockerRepository.deleteById(id);
    }


}
