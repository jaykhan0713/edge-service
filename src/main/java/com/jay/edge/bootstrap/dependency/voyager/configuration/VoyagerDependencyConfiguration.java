package com.jay.edge.bootstrap.dependency.voyager.configuration;

import com.jay.edge.core.port.dependency.voyager.VoyagerDependency;
import com.jay.edge.infra.outbound.http.client.rest.RestClientFactory;
import com.jay.edge.infra.outbound.http.client.rest.adapter.voyager.VoyagerRestClientAdapter;
import com.jay.edge.infra.outbound.http.client.rest.adapter.voyager.mapping.VoyagerJobResponseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tools.jackson.databind.ObjectMapper;

@Configuration
@Profile("prod")
public class VoyagerDependencyConfiguration {
    private static final String CLIENT_NAME = "voyager";
    private static final String VOYAGER_JOB_URI = "/api/v1/jobs/{jobId}";

    @Bean
    VoyagerDependency voyagerDependency(
            RestClientFactory restClientFactory,
            ObjectMapper objectMapper
    ) {
        return new VoyagerRestClientAdapter(
                restClientFactory.buildClient(CLIENT_NAME),
                CLIENT_NAME,
                VOYAGER_JOB_URI,
                new VoyagerJobResponseMapper(objectMapper)
        );
    }
}
