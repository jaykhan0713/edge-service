package com.jay.edge.core.outbound.http.client.settings;

import java.time.Duration;

import com.jay.edge.core.outbound.resiliency.policy.ResiliencyPolicy;

public record HttpClientSettings(
        String clientName,
        String baseUrl,
        Duration connectTimeout,
        Duration readTimeout,
        ResiliencyPolicy resiliencyPolicy
) {}
