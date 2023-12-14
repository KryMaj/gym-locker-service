package com.gym.gym.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
@EqualsAndHashCode
@Entity
@Table
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

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
    private List<ClientLocker> clientLockers;
}
