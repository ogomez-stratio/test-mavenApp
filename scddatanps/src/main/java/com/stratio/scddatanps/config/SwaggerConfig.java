package com.stratio.scddatanps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("SANITAS CONNECTED DATA: NPS API")
            .description("API for proccess NPS scores. This Api belongs to the data access layer (DAAS)")
            .version("0.0.1-SNAPSHOT")
            .contact(new Contact("Sanitas & Stratio","", ""))
            .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.stratio.scddatanps"))
                    .build()
                .apiInfo(apiInfo());
    }
    
    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(
                "validatorUrl",
                "none",
                "alpha",
                "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 0L);
    }
}
