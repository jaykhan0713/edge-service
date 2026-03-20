package com.jay.edge.api.v1.experiment.model;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EdgeExperimentResponse", description = "Experiment runs on /api/v1/experiment/{experimentId}.")
public record EdgeExperimentResponse(

        @Schema(
                description = "Human-readable message.",
                example = "Experiment Endpoint Success"
        )
        String message,

        @Schema(
                description = "Experiment ID passed as URI param",
                example = "Experiment ID"
        )
        String experimentId,

        @Schema(
                description = "Request id from the incoming identity header. Empty when not provided.",
                example = "a1a7c9a73c4bdcb9acf3175c41371da0"
        )
        String requestId,

        @Schema(
                description = "Products received from downstream",
                example = "[ { 'id': '<uuid>', 'name': 'dark sword', 'description': 'some desc', 'tokenPrice': 200 }, {...} ]"
        )
        List<Product> products
) {
        public record Product(UUID id, String name, String description, int tokenPrice) {}
}
