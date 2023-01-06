package com.pismo.cartoes.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class ValidacaoNumeroContaUnitTest {

    private ValidacaoNumeroConta subjectUnderTest = new ValidacaoNumeroConta();

    @Test
    void deveValidarAccountId() {
        String validAccountIdFormat = "507f191e810c19729de860ea";
        assertDoesNotThrow(() -> subjectUnderTest.validar(validAccountIdFormat));
    }

    @Test
    void deveFalharValidacaoQuandoAccountIdInvalido() {
        String wrongAccountIdFormat = "<>";
        assertThrows(ValidationException.class, () -> subjectUnderTest.validar(wrongAccountIdFormat));
    }
}