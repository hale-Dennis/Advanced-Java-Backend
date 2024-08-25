package com.dennis.web.security;

import com.dennis.web.security.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class WebSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSecurityApplication.class, args);
	}

}
