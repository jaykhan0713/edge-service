package com.jay.edge.core.domain.dependency.shopping;

import java.util.UUID;

public record Product(UUID id, String name, String description, int tokenPrice) {}
