package com.colonb.naafa.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class HashMapConverter<T> {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static HashMap<String, Object> convert(Object object) {
        return objectMapper.convertValue(object, HashMap.class);
    }
}
