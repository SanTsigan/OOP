package ru.nsu.tsyganov.pizza;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.*;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    public static PizzaShopConfig loadConfig(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(filename), PizzaShopConfig.class);
        } catch (IOException | com.fasterxml.jackson.core.exc.StreamReadException | DatabindException e) {
            e.printStackTrace();
            return null;
        }
    }
}
