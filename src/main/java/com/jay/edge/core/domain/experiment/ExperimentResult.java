package com.jay.edge.core.domain.experiment;

import java.util.List;

import com.jay.edge.core.domain.dependency.shopping.Product;

public record ExperimentResult(String msg, List<Product> products) {}
