package com.hlbk.restapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class EmployeeWebSecurityConfig {

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http,
                                                ReactiveAuthenticationManager reactiveAuthenticationManager) {
        return http
                .authenticationManager(reactiveAuthenticationManager)
                .httpBasic()
                .and()

                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/employees").hasRole("USER")
                .anyExchange().permitAll()
                .and()

                .csrf().disable()
                .build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsRepository(final PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    @Bean
    ReactiveAuthenticationManager reactiveAuthenticationManager(MapReactiveUserDetailsService userDetailsRepository,
                                                                PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager manager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsRepository);
        manager.setPasswordEncoder(passwordEncoder);
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
