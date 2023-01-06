package com.pismo.cartoes;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@TestPropertySource(properties = {
        "spring.mongodb.embedded.version=4.4.16",
        "spring.data.mongodb.host=localhost",
        "spring.data.mongodb.port=27027",
        "spring.data.mongodb.database=test",
        "spring.data.mongodb.uri=mongodb://localhost:27027/test"})
@DataMongoTest
@ExtendWith(SpringExtension.class)
class TransacaoApplicationIntegrationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void deveSalvarNaInfraDeTeste(@Autowired MongoTemplate mongoTemplate) {
        // given
        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("chave", "valor")
                .add("Test", "infra")
                .get();
        // when
        mongoTemplate.save(objectToSave, "testCollection");
        // then
        assertThat(mongoTemplate.findAll(DBObject.class, "testCollection"))
                .extracting("Test")
                .containsOnly("infra");
    }

}
