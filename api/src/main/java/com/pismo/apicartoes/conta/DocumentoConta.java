package com.pismo.apicartoes.conta;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class DocumentoConta {
    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("document_number")
    private String documentNumber;

    public DocumentoConta() {
    }

    public DocumentoConta(String accountId) {
        this();
        this.accountId = accountId;
    }

    public DocumentoConta(String accountId, String documentNumber) {
        this(accountId);
        this.documentNumber = documentNumber;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accouuntId) {
        this.accountId = accouuntId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

}
