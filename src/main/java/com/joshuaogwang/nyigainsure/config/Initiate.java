package com.joshuaogwang.nyigainsure.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.africastalking.AfricasTalking;

import ch.qos.logback.classic.Logger;

@Configuration
public class Initiate {
    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(Initiate.class);

    @Autowired
    public Initiate(Config config) {
        AfricasTalking.initialize("", "");

        LOGGER.info("AfricasTalking initiated");
    }
}
