package br.com.alura.alurabank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ValidatorBean {

    @Bean
    Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}

