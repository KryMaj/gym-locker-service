package com.gym.gym.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientLocker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp entry;
    private Timestamp goHome;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;
    @OneToOne
    private Locker locker;
}

