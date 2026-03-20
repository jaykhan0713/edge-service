package com.jay.edge.web.mvc.controller.v1.experiment;

import com.jay.edge.core.context.identity.IdentityContextHolder;
import com.jay.edge.web.mvc.controller.v1.experiment.mapping.ExperimentMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jay.edge.app.experiment.service.ExperimentService;
import com.jay.edge.api.v1.experiment.EdgeExperimentApi;
import com.jay.edge.api.v1.experiment.model.EdgeExperimentResponse;

@RestController
public class ExperimentController implements EdgeExperimentApi {

    private final ExperimentService expService;
    private final ExperimentMapper mapper;

    public ExperimentController(
            ExperimentService expService,
            ExperimentMapper mapper
    ) {
        this.expService = expService;
        this.mapper = mapper;
    }

    @GetMapping("/api/v1/experiments/{experimentId}")
    public EdgeExperimentResponse getExperimentResponse(
            @PathVariable String experimentId
    ) {
        var experimentResult = this.expService.runExperiment();


        return new EdgeExperimentResponse(
                experimentResult.msg(),
                experimentId,
                IdentityContextHolder.context().identity().requestId(),
                experimentResult
                        .products()
                        .stream()
                        .map(mapper::toExperimentProduct)
                        .toList()
        );
    }
}
