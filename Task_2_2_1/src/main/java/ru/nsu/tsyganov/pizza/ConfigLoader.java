package ru.nsu.tsyganov.pizza;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    public static PizzaShopConfig loadConfig(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(filename), PizzaShopConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
