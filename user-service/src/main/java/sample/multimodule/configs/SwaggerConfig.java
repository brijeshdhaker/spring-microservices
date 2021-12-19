/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 *
 * @author brijeshdhaker
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("sample.multimodule.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
        
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "User Service",
                "User Service APIs.",
                "TM",
                "Terms of service",
                new Contact("Brijesh K Dhaker", "www.example.com", "brijeshdhaker@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
