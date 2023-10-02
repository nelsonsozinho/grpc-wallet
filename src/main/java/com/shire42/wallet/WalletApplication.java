package com.shire42.wallet;

import io.grpc.BindableService;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class WalletApplication implements ApplicationRunner {

	@Value("${grpc.server.port}")
	private Integer grpcPort;

	private final List<BindableService> services;

	public WalletApplication(final List<BindableService> services) {
		this.services = services;
	}

	public static void main(String[] args) {
		SpringApplication.run(WalletApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		var builder = ServerBuilder.forPort(grpcPort);
		services.forEach(builder::addService);
		builder.build().start();
	}
}
