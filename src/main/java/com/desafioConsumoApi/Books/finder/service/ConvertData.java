package com.desafioConsumoApi.Books.finder.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConvertData implements IConvertData {

    private static final Logger logger = Logger.getLogger(ConvertData.class.getName());
    private ObjectMapper objectMapper;

    public ConvertData() {
        this.objectMapper = new ObjectMapper();
    }

    public <T> T gettingData(String jsonData, Class<T> clase) {
        if (jsonData == null || jsonData.isEmpty()) {
            logger.warning("Empty input data received");
            return null;
        }

        try {
            logger.info("Starting JSON deserialization...");
            T data = objectMapper.readValue(jsonData, clase);
            if (data == null) {
                logger.warning("Deserialized data is null");
            } else {
                logger.info("JSON deserialization successful.");
            }
            return data;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during JSON deserialization", e);
            return null;
        }
    }
    }

