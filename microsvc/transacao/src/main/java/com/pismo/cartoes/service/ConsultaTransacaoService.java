package com.pismo.cartoes.service;

import com.pismo.apicartoes.transacao.TransacaoCartao;
import com.pismo.cartoes.persistence.TransacaoRepository;
import com.pismo.cartoes.service.mapper.TransacaoMapper;
import com.pismo.util.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ConsultaTransacaoService {

    private TransacaoRepository transacaoRepository;
    private TransacaoMapper transacaoMapper;

    public List<TransacaoCartao> buscarTransacoesPorConta(String accountId) {
        log.trace("Localizando transacoes para conta {}", accountId);

        List<TransacaoCartao> transacoesContaCartao = transacaoRepository
                .findAllByAccountId(accountId)
                .stream()
                .map(transacaoMapper::entityToApi)
                .collect(Collectors.toList());

        if (transacoesContaCartao.isEmpty()) {
            log.debug("Nao Encontrado transacoes");
            throw new NotFoundException(String.format("Nao encontrado Transacoes para conta de Id {%s}", accountId));
        }

        log.debug("Transacoes Localizadas");
        return transacoesContaCartao;
    }
}
