package com.stratio.scddatanps.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ConverterScore {

    public static final String N_A = "N/A";

    public static final String CLIENT_NO_INFORM = "12";

    private final Map<String, String>  mapConverter = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put("1", "0");
            put("2", "1");
            put("3", "2");
            put("4", "3");
            put("5", "4");
            put("6", "5");
            put("7", "6");
            put("8", "7");
            put("9", "8");
            put("10", "9");
            put("11", "10");
            put(ConverterScore.CLIENT_NO_INFORM, ConverterScore.N_A);
        }
    };

    public String decrypt(String encryptScore) {
        return Optional.ofNullable(mapConverter.get(encryptScore))
                .orElse(ConverterScore.N_A);
    }
    
    public String encrypt(String decryptScore) {
        return Optional.of(mapConverter.values().stream()
                        .filter(value -> value.equals(decryptScore))
                        .findFirst()
                        .get())
                .orElse(ConverterScore.N_A);
        
        
    }

}
