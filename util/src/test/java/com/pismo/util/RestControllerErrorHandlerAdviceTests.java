package com.pismo.util;

import com.pismo.util.advice.RestControllerErrorHandlerAdvice;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {RestControllerErrorHandlerAdvice.class})
class RestControllerErrorHandlerAdviceTests {

    @Test
    void contextLoads() {
    }

}
