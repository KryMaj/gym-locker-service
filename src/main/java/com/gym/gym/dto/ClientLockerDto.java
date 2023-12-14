package com.gym.gym.dto;

import lombok.*;

import java.sql.Timestamp;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientLockerDto {
    private Long clientLockerId;
    private Long idClient;
    private Long idLocker;
    private Timestamp entry;
    private Timestamp goHome;


    public ClientLockerDto(Long clientLockerId, Long idClient, Long idLocker, Timestamp entry) {
        this.clientLockerId = clientLockerId;
        this.idClient = idClient;
        this.idLocker = idLocker;
        this.entry = entry;
    }
}
