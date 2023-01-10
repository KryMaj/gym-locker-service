package com.gym.gym.repository;

import com.gym.gym.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

Client findClientByUserId(Long userId);
}
