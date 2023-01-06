package com.pismo.apicartoes.conta;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DocumentoCliente {
    @JsonProperty("document_number")
    @NotEmpty(message = "Numero de documento deve ser CPF ou CNPJ")
    @NotBlank(message = "Numero de documento deve conter informacao")
    @NotNull(message = "Sem informacao do Numero de documento")
    private String documentNumber;

    public DocumentoCliente() { }

    public DocumentoCliente(String documentNumber) {
        this();
        this.documentNumber = documentNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    @Override
    public String toString() {
        return "DocumentoCliente{" +
                "documentNumber='" + documentNumber + '\'' +
                '}';
    }
}
