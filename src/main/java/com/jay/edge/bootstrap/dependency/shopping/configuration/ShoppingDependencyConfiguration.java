package com.jay.edge.bootstrap.dependency.shopping.configuration;

import com.jay.edge.core.port.dependency.shopping.ShoppingDependency;
import com.jay.edge.infra.outbound.http.client.rest.RestClientFactory;
import com.jay.edge.infra.outbound.http.client.rest.adapter.apollo.ApolloRestClientAdapter;
import com.jay.edge.infra.outbound.http.client.rest.adapter.apollo.mapping.ApolloMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShoppingDependencyConfiguration {
    private static final String CLIENT_NAME = "apollo";
    private static final String VOYAGER_JOB_URI = "/api/v1/shopping/products";

    @Bean
    public ShoppingDependency shoppingProductsDependency(
            RestClientFactory restClientFactory
    ) {
        return new ApolloRestClientAdapter(
                restClientFactory.buildClient(CLIENT_NAME),
                CLIENT_NAME,
                VOYAGER_JOB_URI,
                Mappers.getMapper(ApolloMapper.class)
        );
    }

}
