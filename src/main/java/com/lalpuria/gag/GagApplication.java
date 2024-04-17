package com.lalpuria.gag;

import com.lalpuria.gag.configuration.security.RsaKeyConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties(RsaKeyConfigProperties.class)
public class GagApplication {
	public static void main(String[] args) {
		SpringApplication.run(GagApplication.class, args);
	}
}
