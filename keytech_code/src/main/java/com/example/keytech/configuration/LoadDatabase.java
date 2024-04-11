package com.example.keytech.configuration;

import com.example.keytech.entity.Session;
import com.example.keytech.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    private SessionRepository sessionRepository;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            log.info("Preloading " + sessionRepository.save(new Session(
                1L, 2, 4, "11111111"
            )));
            log.info("Preloading " + sessionRepository.save(new Session(
                2L, 3, 3, "110010101"
            )));
        };
    }
}
