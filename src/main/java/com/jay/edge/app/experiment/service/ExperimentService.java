package com.jay.edge.app.experiment.service;

import java.util.UUID;

import com.jay.edge.core.domain.experiment.ExperimentResult;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import com.jay.edge.core.port.dependency.voyager.VoyagerDependency;

@Service
@Profile("prod")
public class ExperimentService {
    private final VoyagerDependency voyagerDependency;

    public ExperimentService(VoyagerDependency voyagerDependency) {
        this.voyagerDependency = voyagerDependency;
    }

    public ExperimentResult runExperiment() {
        var result = this.voyagerDependency.voyagerGetJob(UUID.randomUUID());
        return new ExperimentResult(result.msg());
    }
}
