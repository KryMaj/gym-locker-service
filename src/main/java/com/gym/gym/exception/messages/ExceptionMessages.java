package com.gym.gym.exception.messages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessages {

    ENTITY_FOR_PROVIDED_ID_NOT_FOUND("Encja %s dla podanego id:%s nie istnieje"),
    USER_FOR_PROVIDED_USERNAME_NOT_FOUND("Uzytkownik %s nie istnieje"),
    AVERAGE_TIME_IS_UNAVAILABLE("Średni czas jest niedostępny"),
    ENTITY_NOT_FOUND("Encja nie istnieje");

    private final String message;

}
