package com.ukelink.voip.UmCc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Slf4j
public class UmCcApplication {

	public static void main(String[] args) {
		log.info("umcc start running");
		SpringApplication.run(UmCcApplication.class, args);
	}

}
