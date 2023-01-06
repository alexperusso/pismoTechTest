package com.pismo.cartoes.service;

import com.pismo.apicartoes.transacao.OperacaoContaCartao;
import com.pismo.apicartoes.transacao.OperationType;
import com.pismo.apicartoes.transacao.TransacaoCartao;
import com.pismo.cartoes.persistence.TransacaoDocument;
import com.pismo.cartoes.persistence.TransacaoRepository;
import com.pismo.cartoes.service.mapper.TransacaoMapper;
import com.pismo.util.exception.InvalidInputException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class ArmazenaTransacaoService {

    private TransacaoRepository transacaoRepository;
    private TransacaoMapper transacaoMapper;

    public TransacaoCartao salvar(OperacaoContaCartao operacaoContaCartao) {
        log.trace("Armazenando nova transacao");

        TransacaoDocument transacaoDocument = this.criarTransacao(operacaoContaCartao);
        try {
            transacaoDocument = transacaoRepository.save(transacaoDocument);
        } catch (Exception excp) {
            log.debug("Erro ao armazenar transacao em conta do cliente {}", operacaoContaCartao);
            log.error("Erro ao armazenar transacao", excp);
            throw new InvalidInputException(excp.getMessage());
        }

        log.debug("Transacao armazenada");
        return transacaoMapper.entityToApi(transacaoDocument);
    }

    private TransacaoDocument criarTransacao(final OperacaoContaCartao operacaoContaCartao) {
        log.trace("Criando Documento de transacao");

        TransacaoDocument transacaoDocument = new TransacaoDocument();

        transacaoDocument.setAccountId(operacaoContaCartao.getAccountId());
        transacaoDocument.setEventDate(LocalDateTime.now());
        transacaoDocument.setOperationTypeId(operacaoContaCartao.getOperationTypeId().getCodOperacao());

        if (OperationType.PAGAMENTO.equals(operacaoContaCartao.getOperationTypeId())) {
            transacaoDocument.setAmount(operacaoContaCartao.getAmount());
        } else {
            transacaoDocument.setAmount(operacaoContaCartao.getAmount().negate());
        }

        log.debug("Documento de transacao criado");
        return transacaoDocument;
    }

}
