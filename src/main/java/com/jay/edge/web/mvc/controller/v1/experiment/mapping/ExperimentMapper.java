package com.jay.edge.web.mvc.controller.v1.experiment.mapping;

import com.jay.edge.api.v1.experiment.model.EdgeExperimentResponse;
import com.jay.edge.core.domain.dependency.shopping.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExperimentMapper {
    EdgeExperimentResponse.Product toExperimentProduct(Product product);
}
