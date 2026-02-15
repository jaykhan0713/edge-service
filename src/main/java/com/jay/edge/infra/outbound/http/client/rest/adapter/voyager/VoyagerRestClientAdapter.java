package com.jay.edge.infra.outbound.http.client.rest.adapter.voyager;

import java.util.UUID;

import org.springframework.web.client.RestClient;

import com.jay.edge.core.domain.dependency.voyager.VoyagerJobResult;
import com.jay.edge.core.port.dependency.voyager.VoyagerDependency;
import com.jay.edge.infra.outbound.http.client.rest.adapter.voyager.mapping.VoyagerJobResponseMapper;
import com.jay.edge.infra.outbound.http.client.rest.error.RestClientExceptionTranslator;
import com.jay.voyager.api.v1.jobs.model.VoyagerJobResponse;


public class VoyagerRestClientAdapter implements VoyagerDependency {
    private final RestClient restClient;
    private final String clientName;
    private final String uri;
    private final VoyagerJobResponseMapper dtoMapper;

    public VoyagerRestClientAdapter(
            RestClient restClient,
            String clientName,
            String uri,
            VoyagerJobResponseMapper dtoMapper
    ) {
        this.restClient = restClient;
        this.clientName = clientName;
        this.uri = uri;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public VoyagerJobResult voyagerGetJob(UUID jobId) {
        VoyagerJobResponse response =
                RestClientExceptionTranslator.execute(
                        () -> {
                            var spec = restClient
                                    .get()
                                    .uri(uri, jobId)
                                    .retrieve();

                            spec = RestClientExceptionTranslator.applyDefaultOnStatusHandlers(spec, clientName);

                            return spec.body(VoyagerJobResponse.class);
                        },
                        clientName
                );

        return dtoMapper.map(response);
    }
}
