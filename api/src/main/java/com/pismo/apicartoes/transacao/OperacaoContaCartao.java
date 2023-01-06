package com.pismo.apicartoes.transacao;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class OperacaoContaCartao {

    @NotNull(message = "accountId sem informacoes")
    @NotEmpty(message = "accountId deve ter numero do documento da conta")
    @JsonProperty("account_id")
    private String accountId;

    @NotNull(message = "operation_type_id deve ter codigo de operacao valido")
    @JsonProperty("operation_type_id")
    private OperationType operationTypeId;

    @NotNull(message = "amount deve ter valor monetario informado")
    @PositiveOrZero(message = "amount deve ser maior que zero")
    @JsonProperty("amount")
    private BigDecimal amount;

    public OperacaoContaCartao() {
    }

    public OperacaoContaCartao(String accountId, OperationType operationTypeId, BigDecimal amount) {
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public OperationType getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(OperationType operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OperacaoContaCartao{" +
                "accountId='" + accountId + '\'' +
                ", operationTypeId=" + operationTypeId +
                ", amount=" + amount +
                '}';
    }
}
