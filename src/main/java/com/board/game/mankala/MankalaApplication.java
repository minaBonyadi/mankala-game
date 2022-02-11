package com.board.game.mankala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableSwagger2
public class MankalaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MankalaApplication.class, args);
	}

}
