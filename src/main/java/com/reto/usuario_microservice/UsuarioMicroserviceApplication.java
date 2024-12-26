package com.reto.usuario_microservice;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@EnableFeignClients
@SpringBootApplication
public class UsuarioMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuarioMicroserviceApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenApi(){
	 	return new OpenAPI()
	 			.info(new Info()
						.title("Microservice Usuarios")
	 					.version("0.1")
	 					.description("Microservicio para la gestion de usuarios")
						.termsOfService("https://github.com/MiguelBmDv/userMicroservice")
						.license(new License().name("GitLab").url("https://gitlab.com/retoplazoleta/UsuarioMicroservice")));
	 }					

}
