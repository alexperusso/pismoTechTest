package com.pismo.cartoes.controller;


import com.pismo.apicartoes.transacao.OperacaoContaCartao;
import com.pismo.apicartoes.transacao.TransacaoCartao;
import com.pismo.apicartoes.transacao.TransactionRestAdapter;
import com.pismo.cartoes.service.ArmazenaTransacaoService;
import com.pismo.cartoes.service.ConsultaTransacaoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class TransacaoRestController implements TransactionRestAdapter {

    private ArmazenaTransacaoService armazenaTransacaoService;
    private ConsultaTransacaoService consultaTransacaoService;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public TransacaoCartao armazenaTransacao(
            @RequestBody @Valid OperacaoContaCartao operacaoContaCartao
    ) {
        return armazenaTransacaoService.salvar(operacaoContaCartao);
    }

    @Override
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public List<TransacaoCartao> consultaTransacoesConta(
            @PathVariable(required = true) String accountId
    ) {
        return consultaTransacaoService.buscarTransacoesPorConta(accountId);
    }
}
