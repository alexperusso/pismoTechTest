package com.pismo.cartoes.service;

import com.pismo.apicartoes.conta.DocumentoConta;
import com.pismo.cartoes.persistence.ContasDocument;
import com.pismo.cartoes.persistence.ContasRepository;
import com.pismo.util.annotation.LocalPerformanceTrace;
import com.pismo.util.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ConsultarContaService {

    private ContasRepository contasRepository;

    @LocalPerformanceTrace
    public DocumentoConta buscar(String accountId) {
        log.trace("Localizando conta {}", accountId);

        ContasDocument contasDocument = contasRepository
                .findById(accountId)
                .orElseThrow(() -> new NotFoundException(String.format("Conta de Id {%s} Nao encontrada", accountId)));

        log.debug("Conta Localizada");
        return new DocumentoConta(contasDocument.getId().toString(), contasDocument.getDocumentNumber());
    }
}
