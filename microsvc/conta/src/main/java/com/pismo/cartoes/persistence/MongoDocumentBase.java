package com.pismo.cartoes.persistence;

import lombok.NoArgsConstructor;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

@NoArgsConstructor
public abstract class MongoDocumentBase {
    @Id
    private ObjectId id;

    @Version
    private Integer version;

    public MongoDocumentBase(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MongoDocumentBase that = (MongoDocumentBase) o;

        return new EqualsBuilder().append(id, that.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    @Override
    public String toString() {
        return "MongoDocument{" +
                "id=" + id +
                ", version=" + version +
                '}';
    }
}
