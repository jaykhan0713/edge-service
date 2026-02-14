package com.jay.edge;

import java.time.Instant;
import java.util.UUID;

import com.jay.voyager.api.v1.jobs.model.VoyagerJobResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Starter {

    private Starter() {}

    static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }
}
