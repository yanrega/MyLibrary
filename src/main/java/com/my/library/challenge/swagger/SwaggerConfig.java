package com.my.library.challenge.swagger;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.my.library.challenge.controller"))
                .paths(postPaths())
                .build()
                .apiInfo(apiInfo());
    }

    private Predicate<String> postPaths() {
        return or(regex("/book.*"),
                regex("/student.*"),
                regex("/loan.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Library Challenge for Test")
                .description("Spring boot rest API")
                .contact(contact())
                .version("1.0.0")
                .build();
    }

    public Contact contact() {
        return new Contact("Yan Rega Panjaitan", "https://github.com/", "@gmail.com");
    }
}

