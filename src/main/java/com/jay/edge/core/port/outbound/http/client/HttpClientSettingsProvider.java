package com.jay.edge.core.port.outbound.http.client;

import java.util.List;

import com.jay.edge.core.outbound.http.client.settings.HttpClientSettings;

public interface HttpClientSettingsProvider {

    List<HttpClientSettings> provide();
}
