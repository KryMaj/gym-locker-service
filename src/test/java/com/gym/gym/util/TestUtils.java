package com.gym.gym.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    public static <T> String toJson(T o) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(o);
    }
}
