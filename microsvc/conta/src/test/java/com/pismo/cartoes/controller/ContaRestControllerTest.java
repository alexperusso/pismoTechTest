package com.pismo.cartoes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.apicartoes.conta.DocumentoConta;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

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
class ContaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void deveCadastrarContaComSucesso() {
        //language=JSON
        String requestJson = "{\"document_number\":\"76080601079\"}";

        mockMvc.perform(post("/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.account_id").isNotEmpty())
                .andExpect(jsonPath("$.document_number").value("76080601079"));
    }

    @Test
    @SneakyThrows
    void deveConsultarContaCadastrada() {
        String requestJson = "{\"document_number\":\"08291982023\"}";

        String contaCadastrada = mockMvc.perform(post("/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        DocumentoConta documentoConta = objectMapper.readValue(contaCadastrada, DocumentoConta.class);

        mockMvc.perform(get("/v1/accounts/" + documentoConta.getAccountId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.account_id").value(documentoConta.getAccountId()))
                .andExpect(jsonPath("$.document_number").value(documentoConta.getDocumentNumber()));
    }


    @Test
    @SneakyThrows
    void deveGerarErroDeRequisicaoQuandoNaoInformaDocumento() {
        //language=JSON
        String requestJson = "{\"document_number\":\" \"}";

        mockMvc.perform(post("/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void deveGerarErroDeProcessamentoQuandoInformaDocumentoInvalido() {
        //language=JSON
        String requestJson = "{\"document_number\":\"12345\"}";

        mockMvc.perform(post("/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}