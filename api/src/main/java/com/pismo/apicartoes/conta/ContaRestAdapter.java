package com.pismo.apicartoes.conta;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "ContaMicroSvc", description = "API REST para registro de clientes.")
public interface ContaRestAdapter {

    @Operation(summary = "Registra nova conta de cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conta Criada."),
            @ApiResponse(responseCode = "400", description = "Bad Request, formato de request invalido."),
            @ApiResponse(responseCode = "422", description = "Não Processado, erro no processamento do cadastro."),
            @ApiResponse(responseCode = "500", description = "Indisponibilidade de serviço.")
    })
    @PostMapping(path = "/accounts", consumes = "application/json")
    DocumentoConta cadastrarConta(
            @RequestBody @Valid DocumentoCliente documentoCliente
    );

    @Operation(summary = "Consulta informacoes no cadastro de conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "listagem de pautas cadastradas."),
            @ApiResponse(responseCode = "400", description = "Bad Request, formato de request invalido."),
            @ApiResponse(responseCode = "404", description = "Not found, conta nao encontrada.")
    })
    @GetMapping(value = "/accounts/{accountId}", produces = "application/json")
    DocumentoConta consultarConta(
            @PathVariable(required = true) String accountId
    );
}
