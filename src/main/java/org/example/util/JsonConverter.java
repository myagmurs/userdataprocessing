package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter
public class JsonConverter implements AttributeConverter<String, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to String", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, String.class);
        } catch (IOException e) {
            throw new RuntimeException("Error converting String to JSON", e);
        }
    }
}