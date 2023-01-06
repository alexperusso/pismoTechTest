package com.pismo.infra;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
class DiscoveryServerappTest {

	@LocalServerPort
    private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;


	@Test
	void contextLoads() {
	}

	@Test
	public void catalogLoads() {
		@SuppressWarnings("rawtypes")
		String endpoint = "http://localhost:" + port + "/eureka/apps";
		ResponseEntity<Map> entity = testRestTemplate.getForEntity(endpoint, Map.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
