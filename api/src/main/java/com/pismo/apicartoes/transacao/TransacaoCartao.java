package com.pismo.apicartoes.transacao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoCartao {
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("operation_type_id")
    private Integer operationTypeId;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("event_date")
    private LocalDateTime eventDate;

    public TransacaoCartao() {
    }

    public TransacaoCartao(String transactionId, String accountId, Integer operationTypeId, BigDecimal amount, LocalDateTime eventDate) {
        this();
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.amount = amount;
        this.eventDate = eventDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Integer operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }
}
