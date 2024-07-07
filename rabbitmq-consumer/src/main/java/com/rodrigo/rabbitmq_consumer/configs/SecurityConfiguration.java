package com.rodrigo.rabbitmq_consumer.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("12345"))
            .roles("USUARIO")
            .build();
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("12345"))
            .roles("USUARIO", "ADMINISTRADOR")
            .build();   
        return new InMemoryUserDetailsManager(user, admin);     
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(HttpMethod.GET, "/pacientes/novo").hasAnyRole("ADMINISTRADOR", "USUARIO")
                .requestMatchers(HttpMethod.GET, "/pacientes/salvar").hasAnyRole("ADMINISTRADOR", "USUARIO")
                .requestMatchers(HttpMethod.GET, "/pacientes/list").hasAnyRole("ADMINISTRADOR", "USUARIO")
                .requestMatchers(HttpMethod.GET, "/pacientes/edit/**").hasAnyRole("ADMINISTRADOR", "USUARIO")
                .requestMatchers(HttpMethod.GET, "/pacientes/gerarPdf/**").hasAnyRole("ADMINISTRADOR", "USUARIO")
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/index")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
            );
        return http.build();
    }
}
