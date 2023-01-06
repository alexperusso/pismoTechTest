package com.pismo.cartoes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.apicartoes.conta.DocumentoConta;
import com.pismo.apicartoes.transacao.TransacaoCartao;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@TestPropertySource(properties = {
        "spring.mongodb.embedded.version=4.4.16",
        "spring.data.mongodb.host=localhost",
        "spring.data.mongodb.port=27027",
        "spring.data.mongodb.database=test",
        "spring.data.mongodb.uri=mongodb://localhost:27027/test",
        "spring.mvc.pathmatch.matching-strategy=ant_path_matcher"
})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TransacaoRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void deveArmazenarTransacao() {
        //language=JSON
        String requestJson = "{\n" +
                "  \"account_id\": \"507f191e810c19729de860ea\",\n" +
                "  \"operation_type_id\": 4,\n" +
                "  \"amount\": 123.45\n" +
                "}";

        mockMvc.perform(post("/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transaction_id").isNotEmpty())
                .andExpect(jsonPath("$.event_date").isNotEmpty());
    }

    @Test
    @SneakyThrows
    void deveConsultaTransacoesConta() {
        //language=JSON
        String requestJson = "{\n" +
                "  \"account_id\": \"507f191e810c19729de860ea\",\n" +
                "  \"operation_type_id\": 4,\n" +
                "  \"amount\": 123.45\n" +
                "}";

        String transacaoArmazenada = mockMvc.perform(post("/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        TransacaoCartao transacao = objectMapper.readValue(transacaoArmazenada, TransacaoCartao.class);

        mockMvc.perform(get("/v1/transactions/" + transacao.getAccountId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.[0].transaction_id").value(transacao.getTransactionId()))
                .andExpect(jsonPath("$.[0].event_date")
                        .value(transacao.getEventDate().truncatedTo(ChronoUnit.MILLIS).toString()));
    }


    @Test
    @SneakyThrows
    void deveGerarErroDeRequisicaoQuandoInformaTipoDeOperacaoInvalido() {
        //language=JSON
        String requestJson = "{\n" +
                "  \"account_id\": \"507f191e810c19729de860ea\",\n" +
                "  \"operation_type_id\": 99,\n" +
                "  \"amount\": 123.45\n" +
                "}";

        mockMvc.perform(post("/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}