package br.com.alura.alurabank.configuracoes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.awt.image.BufferedImage;

@Configuration
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/qr-code/**").permitAll()
                .antMatchers("/listen/**").permitAll()
                .antMatchers("/internet-bank").hasAuthority("SCOPE_account-management")
                .antMatchers("/contas/extrato").hasAuthority("SCOPE_read-balance")
                .anyRequest().authenticated()
                .and().oauth2ResourceServer().jwt();

        return http.build();
    }


}
