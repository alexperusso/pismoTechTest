package com.pismo.infra;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("local")
class GatewayApplicationTests {

    @Value("${wiremock.server.port}")
    private String wiremockPort;

    @Autowired
    private WebTestClient webClient;

    @Test
    void contextLoads() {
    }

    @Test
    void gatewayDeveRemoverNomeDeServico_eRedirecionarParaServicoComRotaValida() {
        stubFor(get(urlEqualTo("/teste"))
                .willReturn(okJson("{\"headers\":{\"Hello\":\"World\"}}")));

        webClient.get().uri("/contas/teste")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.headers.Hello").isEqualTo("World");
    }

}
