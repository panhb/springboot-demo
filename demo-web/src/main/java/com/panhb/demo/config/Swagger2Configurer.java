package com.panhb.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author panhb
 */
@Configuration
@EnableSwagger2
public class Swagger2Configurer {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfoV5())
                .select()
                .apis(RequestHandlerSelectors.basePackage("*"))
                .paths(PathSelectors.any())
                .build();
    }
    
    private ApiInfo apiInfoV5() {
        return new ApiInfoBuilder()
                .title("demo")
                .description("更多详情请关注: http://panhb.github.io/")
                .termsOfServiceUrl("http://panhb.github.io/")
                .version("1.0")
                .build();
    }
    

}