package com.pismo.cartoes.validation;

import com.pismo.apicartoes.conta.DocumentoCliente;
import com.pismo.util.exception.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
@Slf4j
public class ValidacaoDocumentoCliente {
    private class ValidadorDocumento {

        @CPF(message = "CPF invalido")
        private String cpf;

        @CNPJ(message = "CNPJ invalido")
        private String cnpj;

        public ValidadorDocumento(String cpf, String cnpj) {
            this.cpf = cpf;
            this.cnpj = cnpj;
        }
    }

    private Validator validator;

    public ValidacaoDocumentoCliente() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public void validar(DocumentoCliente documentoCliente) {
        log.debug("Validando documento do cliente");

        ValidadorDocumento documentoConta = this.identificarDocumento(documentoCliente);

        Set<ConstraintViolation<ValidadorDocumento>> violacoes = validator.validate(documentoConta);
        if (!violacoes.isEmpty()) {
            throw new InvalidInputException("Documento de cliente invalido");
        }
    }

    private ValidadorDocumento identificarDocumento(DocumentoCliente documentoCliente) {
        if (documentoCliente.getDocumentNumber().length() > 11) {
            log.trace("Documento identificado como CNPJ");
            return new ValidadorDocumento(null, documentoCliente.getDocumentNumber());
        }

        log.trace("Documento identificado como CPF");
        return new ValidadorDocumento(documentoCliente.getDocumentNumber(), null);
    }
}


