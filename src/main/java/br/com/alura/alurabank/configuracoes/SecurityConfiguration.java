package br.com.alura.alurabank.configuracoes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/token").hasAuthority("SCOPE_customer")
                .anyRequest().authenticated()
                .and().oauth2ResourceServer().jwt();

        return http.build();
    }


}
