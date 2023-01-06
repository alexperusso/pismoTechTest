package com.pismo.apicartoes.transacao;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "TransacaoMicroSvc", description = "API REST para registro das transacoes.")
public interface TransactionRestAdapter {

    @Operation(summary = "Registra transacao de debito e credito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transacao Armazenada."),
            @ApiResponse(responseCode = "400", description = "Bad Request, formato de request invalido."),
            @ApiResponse(responseCode = "422", description = "Não Processado, erro no processamento do cadastro."),
            @ApiResponse(responseCode = "500", description = "Indisponibilidade de serviço.")
    })
    @PostMapping(value = "/transactions", consumes = "application/json")
    TransacaoCartao armazenaTransacao(
            @RequestBody @Valid OperacaoContaCartao operacaoContaCartao
    );

    @GetMapping(value = "/transactions/{accountId}", produces = "application/json")
    List<TransacaoCartao> consultaTransacoesConta(
            @PathVariable(required = true) String accountId
    );
}
