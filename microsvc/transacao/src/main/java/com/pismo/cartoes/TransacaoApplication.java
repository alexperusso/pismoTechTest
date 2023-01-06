package com.pismo.cartoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoRepositories(basePackages = "com.pismo.cartoes.persistence")
@ComponentScan(basePackages = {
		"com.pismo.apicartoes",
		"com.pismo.util",
		"com.pismo.cartoes" })
public class TransacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransacaoApplication.class, args);
	}

}
