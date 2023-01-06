package com.pismo.apicartoes.transacao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.ValidationException;
import java.util.EnumSet;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OperationType {
    COMPRA_A_VISTA(1),
    COMPRA_PARCELADA(2),
    SAQUE(3),
    PAGAMENTO(4);

    private final int codOperacao;

    OperationType(int codOperacao) {
        this.codOperacao = codOperacao;
    }

    public int getCodOperacao() {
        return this.codOperacao;
    }

    @JsonCreator
    public static OperationType fromValue(int codigo) throws ValidationException {
        return EnumSet.allOf(OperationType.class)
                .stream()
                .filter(tipos -> tipos.getCodOperacao() == codigo)
                .findFirst()
                .orElseThrow(() -> new ValidationException(String.format("Tipo de Operacao Invalido [%s]", codigo)));
    }
}
