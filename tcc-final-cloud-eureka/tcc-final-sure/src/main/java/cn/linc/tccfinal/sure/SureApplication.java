package cn.linc.tccfinal.sure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages ="org.tcc")
@EnableHystrix
@ComponentScan(basePackages ="cn.linc")
@ComponentScan(basePackages ="org.tcc")
public class SureApplication {
	public static void main(String[] args) {
		SpringApplication.run(SureApplication.class, args);
	}
}
