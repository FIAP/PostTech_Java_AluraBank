package br.com.alura.alurabank.configuracoes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;

@Configuration
public class HttpConfigurations {

    @Bean
    public BufferedImageHttpMessageConverter bufferedImageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
}
