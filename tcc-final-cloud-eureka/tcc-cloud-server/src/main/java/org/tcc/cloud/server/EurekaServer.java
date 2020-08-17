package org.tcc.cloud.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer {
	public static void main(String[] args) {
        SpringApplication.run(EurekaServer.class, args);
    }
}
