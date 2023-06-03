package com.sv.gatewayservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {


    private final JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers("/api/oauth/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/products/**").permitAll()
////                        "/api/items/listar",
////                        "/api/usuarios/usuarios",
////                        "/api/items/ver/{id}/cantidad/{cantidad}",
//                        "/api/products/{id}").permitAll()
//                .pathMatchers(HttpMethod.GET, "/api/users/{id}").hasAnyRole("ADMIN", "USER")
//                .pathMatchers("/api/productos/**", "/api/items/**", "/api/usuarios/usuarios/**").hasRole("ADMIN")
//                .anyExchange().authenticated()
//                .and().addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .anyExchange().authenticated().and().csrf().disable()
                .build();
    }


}
