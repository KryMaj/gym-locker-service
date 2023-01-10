package com.gym.gym.mapper;

import com.gym.gym.dto.LockerDto;
import com.gym.gym.entity.Locker;

public interface LockerMapper {

    static LockerDto toDto(Locker locker) {
        return LockerDto.builder()
                .lockerId(locker.getLockerId())
                .isAvailable(locker.isAvailable())
                .isAWomenLocker(locker.isAWomenLocker())
                .build();
    }

    static Locker toEntity(LockerDto lockerDto) {
        return Locker.builder()
                .lockerId(lockerDto.getLockerId())
                .isAvailable(lockerDto.isAvailable())
                .isAWomenLocker(lockerDto.isAWomenLocker())
                .build();
    }
}
