package com.movie.backend.test;

import org.junit.jupiter.api.*;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.logging.Logger;

public class SimpleTest {
    @Test
    @DisplayName("test starts")
    @BeforeTestExecution
    public void startTest() {
        Logger logger = Logger.getLogger("text1");
        logger.info("test starts");
    }

    @Test
    @DisplayName("test ends")
    public void endTest() {
        Logger logger = Logger.getLogger("text1");
        logger.info("test ends");
    }

}
