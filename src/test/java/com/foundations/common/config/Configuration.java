package com.foundations.common.config;

import io.restassured.RestAssured;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

@Slf4j
@Getter
public class Configuration {
    /**
     *
     * @author Nidhi SHukla
     */
    private final Properties properties = new Properties();
    private final String profile;

    public Configuration() {
        profile = System.getProperty("profile");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        loadProperties();
    }
//loading application property in system
    private void loadProperties() {
        String filename = String.format("application%s.properties", Optional.ofNullable(profile)
                                                                            .map("-"::concat)
                                                                            .orElse(""));
        try {
            properties.load(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)));
            for (String name : properties.stringPropertyNames()) {
                String value = properties.getProperty(name);
                System.setProperty(name, value);
            }
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong reading the config file");
        }
    }
}
