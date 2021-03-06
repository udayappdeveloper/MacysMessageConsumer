package com.macys;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@SpringBootApplication
@EnableSwagger2
public class MacysConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MacysConsumerApplication.class, args);
    }

    @Bean
    public Docket getCustomizedDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.macys"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "MACY's RabbitMQ Use Case Application API",
                "Macys Consumer Api Use Case",
                "1.0",
                "",
                new Contact("UdayKumar", "", "udaykumar.kalluri@zensar.com"),
                "GPL",
                "www.gpl.com",
                new ArrayList<>()
        );
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
    
    
}
