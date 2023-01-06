package com.pismo.cartoes.controller;


import com.pismo.apicartoes.conta.ContaRestAdapter;
import com.pismo.apicartoes.conta.DocumentoCliente;
import com.pismo.apicartoes.conta.DocumentoConta;
import com.pismo.cartoes.service.CadastroContaService;
import com.pismo.cartoes.service.ConsultarContaService;
import com.pismo.cartoes.validation.ValidacaoDocumentoCliente;
import com.pismo.cartoes.validation.ValidacaoNumeroConta;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ContaRestController implements ContaRestAdapter {

    private final ValidacaoDocumentoCliente validacaoDocumentoCliente;
    private final ValidacaoNumeroConta validacaoNumeroConta;
    private final CadastroContaService cadastroContaService;
    private final ConsultarContaService consultarContaService;


    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public DocumentoConta cadastrarConta(
            @RequestBody @Valid DocumentoCliente documentoCliente
    ) {
        validacaoDocumentoCliente.validar(documentoCliente);
        return cadastroContaService.cadastrar(documentoCliente);
    }

    @Override
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public DocumentoConta consultarConta(
            @PathVariable(required = true) String accountId
    ) {
        validacaoNumeroConta.validar(accountId);
        return consultarContaService.buscar(accountId);
    }
}
