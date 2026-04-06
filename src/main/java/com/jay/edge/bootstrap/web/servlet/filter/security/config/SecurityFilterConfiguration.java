package com.jay.edge.bootstrap.web.servlet.filter.security.config;

import java.util.List;

import com.jay.edge.core.error.api.ErrorType;
import com.jay.edge.web.servlet.error.ErrorResponseWriter;
import org.springframework.boot.security.oauth2.server.resource.autoconfigure.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityFilterConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationConverter converter,
            AuthenticationEntryPoint authenticationEntryPoint
    ) {
        return http
                .csrf(csrf -> csrf.disable()) //not a session
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml"
                        ).permitAll()
                        .requestMatchers("/api/smoke").permitAll()
                        .requestMatchers("/api/v1/experiments/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(converter))
                        //.jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .anonymous(Customizer.withDefaults()) //enables anonymous token
                .build();
    }

    @Bean // Eager check issuer is valid.
    public JwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
        return JwtDecoders.fromIssuerLocation(
                properties.getJwt().getIssuerUri()
        );
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String scope = jwt.getClaimAsString("scope");
            if (scope == null || !scope.contains("openid")) {
                throw new JwtException("Missing required scope: openid");
            }
            return List.of(new SimpleGrantedAuthority("SCOPE_openid"));
        });

        return converter;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(ErrorResponseWriter errorResponseWriter) {
        return (_, response, _) -> {
            errorResponseWriter.writeJsonErrorResponse(response, ErrorType.UNAUTHORIZED);
        };
    }
}
