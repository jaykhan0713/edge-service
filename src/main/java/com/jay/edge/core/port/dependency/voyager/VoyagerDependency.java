package com.jay.edge.core.port.dependency.voyager;

import java.util.UUID;

import com.jay.edge.core.domain.dependency.voyager.VoyagerJobResult;

public interface VoyagerDependency {
    VoyagerJobResult voyagerGetJob(UUID jobId);
}
