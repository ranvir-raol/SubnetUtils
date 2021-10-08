package com.example.SubnetUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SubnetUtilsApplication {

	@Autowired
	SubnetLoader subnetLoader;
	
	public static void main(String[] args) {
		SpringApplication.run(SubnetUtilsApplication.class, args);
		
	}
	
	

}
