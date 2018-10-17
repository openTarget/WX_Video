package com.lhn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages="com.lhn.mapper")
@ComponentScan(basePackages = {"com.lhn","idworker"})
public class  Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
