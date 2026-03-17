package com.jay.edge.core.domain.dependency.apollo;

import java.util.UUID;

public record ApolloProduct(UUID id, String name, String description, int tokenPrice) {}
