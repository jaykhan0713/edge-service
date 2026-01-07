package com.jay.edge.bootstrap.concurrent.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jay.edge.infra.concurrent.propagation.ContextPropagator;
import com.jay.edge.infra.concurrent.propagation.identity.IdentityContextPropagator;
import com.jay.edge.infra.concurrent.propagation.mdc.MdcContextPropagator;

@Configuration
public class ContextPropagationConfiguration {

    @Bean
    public ContextPropagator identityContextPropagator() {
        return new IdentityContextPropagator();
    }

    @Bean
    public ContextPropagator mdcContextPropagator() {
        return new MdcContextPropagator();
    }
}
