package com.jay.edge.infra.outbound.http.client.rest.adapter.apollo.mapping;

import com.jay.apollo.api.v1.shopping.ProductDto;
import com.jay.edge.core.domain.dependency.shopping.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ApolloMapper {
    Product toApolloProduct(ProductDto productDto);
}
