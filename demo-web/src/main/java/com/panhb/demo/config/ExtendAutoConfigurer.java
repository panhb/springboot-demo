package com.panhb.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author panhb
 */
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
