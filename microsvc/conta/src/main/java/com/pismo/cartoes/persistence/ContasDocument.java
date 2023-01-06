package com.pismo.cartoes.persistence;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@NoArgsConstructor
@Getter
@Setter
@Document(collection = "contas")
public class ContasDocument extends MongoDocumentBase {

    @Field("document_number")
    @Indexed(unique = true)
    private String documentNumber;

    public ContasDocument(ObjectId id) {
        super(id);
    }

    public ContasDocument(ObjectId id, String documentNumber) {
        super(id);
        this.documentNumber = documentNumber;
    }
}
