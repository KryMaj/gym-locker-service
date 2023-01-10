package com.gym.gym.repository;

import com.gym.gym.entity.Locker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockerRepository extends JpaRepository<Locker, Long> {

    Locker findLockerByLockerId(Long lockerId);
}
