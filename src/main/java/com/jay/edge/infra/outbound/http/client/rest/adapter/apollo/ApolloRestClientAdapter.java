package com.jay.edge.infra.outbound.http.client.rest.adapter.apollo;

import java.util.Collections;
import java.util.List;

import com.jay.apollo.api.v1.shopping.ShoppingProductsResponse;
import com.jay.edge.core.domain.dependency.shopping.Product;
import com.jay.edge.core.port.dependency.shopping.ShoppingDependency;
import com.jay.edge.infra.outbound.http.client.rest.adapter.apollo.mapping.ApolloMapper;
import com.jay.edge.infra.outbound.http.client.rest.error.RestClientExceptionTranslator;
import org.springframework.web.client.RestClient;

public class ApolloRestClientAdapter implements ShoppingDependency {

    private final RestClient restClient;
    private final String clientName;
    private final String uri;
    private final ApolloMapper mapper;

    public ApolloRestClientAdapter(
            RestClient restClient,
            String clientName,
            String uri,
            ApolloMapper mapper
    ) {

        this.restClient = restClient;
        this.clientName = clientName;
        this.uri = uri;
        this.mapper = mapper;
    }

    @Override
    public List<Product> products() {
        var responseDto = RestClientExceptionTranslator.execute(
                () -> {
                    var spec = restClient
                            .get()
                            .uri(uri)
                            .retrieve();

                    spec = RestClientExceptionTranslator.applyDefaultOnStatusHandlers(spec, clientName);

                    return spec.body(ShoppingProductsResponse.class);
                },
                clientName
        );

        if (responseDto == null || responseDto.productDtos() == null) {
            return Collections.EMPTY_LIST;
        }

        return responseDto.productDtos().stream().map(mapper::toApolloProduct).toList();
    }
}
