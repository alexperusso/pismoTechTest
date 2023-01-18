package com.pismo.cartoes.service;

import com.pismo.apicartoes.conta.DocumentoCliente;
import com.pismo.apicartoes.conta.DocumentoConta;
import com.pismo.cartoes.persistence.ContasDocument;
import com.pismo.cartoes.persistence.ContasRepository;
import com.pismo.util.annotation.LocalPerformanceTrace;
import com.pismo.util.exception.InvalidInputException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CadastroContaService {

    private ContasRepository contasRepository;

    @LocalPerformanceTrace
    public DocumentoConta cadastrar(DocumentoCliente documentoCliente) {
        log.trace("Cadastrando novo documento de conta");

        ContasDocument contasDocument = new ContasDocument();
        contasDocument.setDocumentNumber(documentoCliente.getDocumentNumber());

        try {
            contasDocument = contasRepository.save(contasDocument);
        } catch (Exception excp) {
            log.debug("Erro ao salvar documento de conta do cliente {}", documentoCliente);
            log.error("Erro ao cadastrar conta", excp);
            throw new InvalidInputException(excp.getMessage());
        }

        log.debug("Documento de conta cadastrado {}", contasDocument.getId().toString());
        return new DocumentoConta(contasDocument.getId().toString(), contasDocument.getDocumentNumber());
    }
}
