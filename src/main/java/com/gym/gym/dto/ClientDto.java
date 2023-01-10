package com.gym.gym.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {

    private long userId;
    private String name;
    private String surname;
    private boolean isAWoman;
    private int averageTime;

}
