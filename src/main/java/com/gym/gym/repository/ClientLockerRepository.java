package com.gym.gym.repository;

import com.gym.gym.entity.ClientLocker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientLockerRepository extends JpaRepository<ClientLocker, Long> {


    List<ClientLocker> findAllByGoHomeIsNull();


}
