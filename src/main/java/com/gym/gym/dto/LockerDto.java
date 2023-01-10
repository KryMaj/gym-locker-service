package com.gym.gym.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LockerDto {
    private long lockerId;
    private boolean isAWomenLocker;
    private boolean isAvailable;
}
