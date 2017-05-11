package com.panhb.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtendAutoConfigurer {
	
	@Bean
	public CommandLineRunner customConfigurer(){
	    return new CommandLineRunner() {
	        @Override
	        public void run(String... strings) throws Exception {
	        }
	    };
	}

}
