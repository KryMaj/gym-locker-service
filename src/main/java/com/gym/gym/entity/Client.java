package com.gym.gym.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long userId;
    private String name;
    private String surname;
    private boolean isAWoman;
    private int averageTime;
    @OneToMany(mappedBy = "client")
//    @JoinColumn(name = "client_id")
    private List<ClientLocker> clientLockers;
}
