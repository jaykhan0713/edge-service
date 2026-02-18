package com.jay.edge.web.mvc.controller.v1.experiment;

import com.jay.edge.core.context.identity.IdentityContextHolder;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jay.edge.app.experiment.service.ExperimentService;
import com.jay.edge.api.v1.experiment.EdgeExperimentApi;
import com.jay.edge.api.v1.experiment.model.EdgeExperimentResponse;

@RestController
@Profile("prod")
public class ExperimentController implements EdgeExperimentApi {

    private final ExperimentService expService;

    public ExperimentController(ExperimentService expService) {
        this.expService = expService;
    }

    @GetMapping("/api/v1/experiments/{experimentId}")
    public EdgeExperimentResponse getExperimentResponse(
            @PathVariable String experimentId
    ) {
        var experimentResult = this.expService.runExperiment();

        return new EdgeExperimentResponse(
                experimentResult.msg(),
                experimentId,
                IdentityContextHolder.context().identity().requestId()
        );
    }
}
