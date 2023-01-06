package com.pismo.cartoes.validation;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;

@Component
@NoArgsConstructor
@Slf4j
public class ValidacaoNumeroConta {
    public void validar(String accountId) {
        log.trace("Validando numero de conta");

        try {
            new ObjectId(accountId);
        } catch (Exception excp) {
            log.error("Erro ao validar accountId", excp);
            throw new ValidationException("accountId Invalido");
        }

        log.debug("Numero de conta validado {}", accountId);
    }
}
