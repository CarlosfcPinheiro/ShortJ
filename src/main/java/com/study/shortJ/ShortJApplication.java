package com.study.shortJ;

import com.study.shortJ.config.ShortUrlConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ShortUrlConfig.class)
public class ShortJApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortJApplication.class, args);
	}

}
