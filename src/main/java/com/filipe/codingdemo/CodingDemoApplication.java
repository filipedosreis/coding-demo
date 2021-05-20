package com.filipe.codingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CodingDemoApplication
 * 
 * Name: filipe reis
 * Date: 2021-05-19
 */
@SpringBootApplication
public class CodingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingDemoApplication.class, args);
		CodingDemoResolution solution = new CodingDemoResolution();
		solution.start();
	}

}
