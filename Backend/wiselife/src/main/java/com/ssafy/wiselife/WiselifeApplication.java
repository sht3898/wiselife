package com.ssafy.wiselife;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class WiselifeApplication extends SpringBootServletInitializer implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(WiselifeApplication.class, args);
	}
	
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

}
