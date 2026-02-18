package com.jay.edge.api.v1.experiment;

import com.jay.edge.api.v1.common.error.StandardErrorResponses;
import com.jay.edge.api.v1.experiment.model.EdgeExperimentResponse;
import com.jay.edge.api.v1.sample.model.SampleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "EdgeExperimentApi",
        description = "/api/v1/experiments/{experimentId}"
)
public interface EdgeExperimentApi {

    @Operation(
            summary = "Experiment endpoint",
            description = "Demonstrates edge service to internal services"
    )
    @StandardErrorResponses
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SampleResponse.class)
                    )
            )
    })
    EdgeExperimentResponse getExperimentResponse(String experimentId);
}