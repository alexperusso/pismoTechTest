package com.pismo.cartoes.validation;

import com.pismo.apicartoes.conta.DocumentoCliente;
import com.pismo.util.exception.InvalidInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class ValidacaoDocumentoClienteUnitTest {

    private final ValidacaoDocumentoCliente subjectUnderTest = new ValidacaoDocumentoCliente();

    @Test
    @DisplayName("Validacao CPF")
    void deveValidarCpfCorreto() {
        String cpf_valido = "76080601079";
        DocumentoCliente documentoCliente = new DocumentoCliente(cpf_valido);
        assertDoesNotThrow(() -> subjectUnderTest.validar(documentoCliente));
    }

    @Test
    @DisplayName("Validacao CNPJ")
    void deveValidarCnpjCorreto() {
        String cnpj_valido = "86294589000101";
        DocumentoCliente documentoCliente = new DocumentoCliente(cnpj_valido);
        assertDoesNotThrow(() -> subjectUnderTest.validar(documentoCliente));
    }

    @Test
    @DisplayName("CPF Invalido")
    void deveFalharValidacaoQuandoCpfInvalido() {
        String cpf_invalido = "76080601099";
        DocumentoCliente documentoCliente = new DocumentoCliente(cpf_invalido);
        assertThrows(InvalidInputException.class, () -> subjectUnderTest.validar(documentoCliente));
    }


    @Test
    @DisplayName("Cnpj Invalido")
    void deveFalharValidacaoQuandoCnpjInvalido() {
        String cnpj_invalido = "86294589000199";
        DocumentoCliente documentoCliente = new DocumentoCliente(cnpj_invalido);
        assertThrows(InvalidInputException.class, () -> subjectUnderTest.validar(documentoCliente));
    }
}