package com.jay.edge.bootstrap.dependency.gotenberg.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jay.edge.core.port.dependency.gotenberg.GotenbergDependency;
import com.jay.edge.infra.outbound.http.client.rest.RestClientFactory;
import com.jay.edge.infra.outbound.http.client.rest.adapter.gotenberg.GotenbergRestClientAdapter;

@Configuration
public class GotenbergDependencyConfiguration {
    private static final String CLIENT_NAME = "gotenberg";
    private static final String GOTENBERG_CONVERT_HTML_URI = "/forms/chromium/convert/html";
    private static final String GOTENBERG_CONVERT_OFFICE_URI = "/forms/libreoffice/convert";

    @Bean
    GotenbergDependency gotenbergDependency(
            RestClientFactory restClientFactory
    ) {
        return new GotenbergRestClientAdapter(
                restClientFactory.buildClient(CLIENT_NAME),
                CLIENT_NAME,
                GOTENBERG_CONVERT_HTML_URI,
                GOTENBERG_CONVERT_OFFICE_URI
        );
    }
}
