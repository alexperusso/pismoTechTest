package com.pismo.cartoes.persistence;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "transacoes")
public class TransacaoDocument {

    @Id
    private ObjectId transactionId;

    @Version
    private Integer version;

    @Field("account_id")
    @Indexed(unique = false)
    private String accountId;

    @Field("operation_type_id")
    private Integer operationTypeId;

    @Field("amount")
    private BigDecimal amount;

    @Field("event_date")
    private LocalDateTime eventDate;

}
