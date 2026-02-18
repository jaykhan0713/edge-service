package com.jay.edge.app.experiment.service;

import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.jay.edge.core.port.dependency.voyager.VoyagerDependency;
import com.jay.edge.app.dependency.error.DependencyExceptionTranslator;
import com.jay.edge.core.domain.experiment.ExperimentResult;

@Service
@Profile("prod")
public class ExperimentService {
    private final VoyagerDependency voyagerDependency;

    public ExperimentService(VoyagerDependency voyagerDependency) {
        this.voyagerDependency = voyagerDependency;
    }

    public ExperimentResult runExperiment() {
        UUID jobId = UUID.randomUUID();

        var result = DependencyExceptionTranslator.execute(
                () -> voyagerDependency.voyagerGetJob(jobId)
        );
        return new ExperimentResult(result.msg());
    }
}
