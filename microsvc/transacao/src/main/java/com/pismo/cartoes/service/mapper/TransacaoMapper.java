package com.pismo.cartoes.service.mapper;

import com.pismo.apicartoes.transacao.TransacaoCartao;
import com.pismo.cartoes.persistence.TransacaoDocument;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class TransacaoMapper {

    public abstract TransacaoCartao entityToApi(TransacaoDocument entity);

    public String map(ObjectId value) {
        return value != null ? value.toString() : null;
    }

    public ObjectId map(String value) {
        return value != null ? new ObjectId(value) : null;
    }

    @Mappings({
            @Mapping(target = "version", ignore = true)
    })
    public abstract TransacaoDocument apiToEntity(TransacaoCartao entity);

}
