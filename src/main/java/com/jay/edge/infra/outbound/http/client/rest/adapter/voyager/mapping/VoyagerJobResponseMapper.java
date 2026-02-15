package com.jay.edge.infra.outbound.http.client.rest.adapter.voyager.mapping;

import tools.jackson.databind.ObjectMapper;

import com.jay.edge.core.domain.dependency.voyager.VoyagerJobResult;
import com.jay.voyager.api.v1.jobs.model.VoyagerJobResponse;


public class VoyagerJobResponseMapper {

    private final ObjectMapper objectMapper;

    public VoyagerJobResponseMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public VoyagerJobResult map(VoyagerJobResponse dto) {
        String jsonString = objectMapper.writeValueAsString(dto);
        return new VoyagerJobResult(jsonString);
    }
}
