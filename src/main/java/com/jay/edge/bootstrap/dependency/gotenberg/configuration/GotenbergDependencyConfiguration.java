package com.jay.edge.bootstrap.dependency.gotenberg.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

import com.jay.edge.core.port.dependency.gotenberg.GotenbergDependency;
import com.jay.edge.infra.outbound.http.client.rest.RestClientFactory;
import com.jay.edge.infra.outbound.http.client.rest.adapter.gotenberg.GotenbergRestClientAdapter;

@Configuration
public class GotenbergDependencyConfiguration {
    private static final String CLIENT_NAME = "gotenberg";
    private static final String GOTENBERG_CONVERT_HTML_URI = "/forms/chromium/convert/html";

    @Bean
    GotenbergDependency gotenbergDependency(
            RestClientFactory restClientFactory,
            ObjectMapper objectMapper
    ) {
        return new GotenbergRestClientAdapter(
                restClientFactory.buildClient(CLIENT_NAME),
                CLIENT_NAME,
                GOTENBERG_CONVERT_HTML_URI //TODO: pass in list of strings/enum to map to multiple paths for libreoffice
        );
    }
}
