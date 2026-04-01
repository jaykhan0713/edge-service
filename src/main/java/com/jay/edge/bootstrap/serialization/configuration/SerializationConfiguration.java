package com.jay.edge.bootstrap.serialization.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.module.blackbird.BlackbirdModule;

@Configuration
public class SerializationConfiguration {

    // Don't touch the recycler pool — Jackson 3.x default (concurrentDequePool) is correct for virtual threads
//    @Bean
//    public ObjectMapper objectMapper() {
//        return JsonMapper.builder()
//                .disable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
//                .addModule(new BlackbirdModule())
//                .build();
//    }
}
