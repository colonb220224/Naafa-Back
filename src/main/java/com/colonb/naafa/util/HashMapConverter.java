package com.colonb.naafa.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class HashMapConverter<T> {

    private T object;
    ObjectMapper objectMapper = new ObjectMapper();

    public HashMap<String,Object> convert(T object){
        return objectMapper.convertValue(object,HashMap.class);
    }
}
