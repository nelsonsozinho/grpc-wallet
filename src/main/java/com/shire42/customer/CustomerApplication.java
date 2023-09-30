package com.shire42.customer;

import io.grpc.BindableService;
import io.grpc.ServerBuilder;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class CustomerApplication implements ApplicationRunner {

	private final List<BindableService> services;

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		var builder = ServerBuilder.forPort(9090);
		services.forEach(builder::addService);
		builder.build().start();
	}
}
