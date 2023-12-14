package com.gym.gym.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@EqualsAndHashCode
@Entity
@Table
@Setter
@Getter
@Builder
@AllArgsConstructor

@NoArgsConstructor
public class ClientLocker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //zmiana
    @Column(unique = true)
    private Long clientLockerId;
    private Timestamp entry;
    private Timestamp goHome;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;
    @OneToOne
    private Locker locker;
}

