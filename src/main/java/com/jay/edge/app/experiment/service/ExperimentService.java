package com.jay.edge.app.experiment.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;

import com.jay.edge.core.error.api.ApiException;
import com.jay.edge.core.port.dependency.shopping.ShoppingDependency;
import org.springframework.stereotype.Service;

import com.jay.edge.core.port.dependency.voyager.VoyagerDependency;
import com.jay.edge.app.dependency.error.DependencyExceptionTranslator;
import com.jay.edge.core.domain.experiment.ExperimentResult;

@Service
public class ExperimentService {
    private final VoyagerDependency voyagerDependency;
    private final ShoppingDependency shoppingDependency;
    private final ExecutorService executorService;

    public ExperimentService(
            VoyagerDependency voyagerDependency,
            ShoppingDependency shoppingDependency,
            ExecutorService platformVirtualThreadExecutorService
    ) {
        this.voyagerDependency = voyagerDependency;
        this.shoppingDependency = shoppingDependency;
        this.executorService = platformVirtualThreadExecutorService;
    }

    public ExperimentResult runExperiment() {
        UUID jobId = UUID.randomUUID();

        var voyagerFuture = CompletableFuture.supplyAsync(
                DependencyExceptionTranslator.wrapExecution(() -> voyagerDependency.voyagerGetJob(jobId)),
                executorService
        );

        var shoppingFuture = CompletableFuture.supplyAsync(
                DependencyExceptionTranslator.wrapExecution(shoppingDependency::products),
                executorService
        );


        // fail fast if one dep call fails. TODO: Future that hasnt failed keeps running in background, write a failfast cancel path
        var allOf = CompletableFuture.allOf(voyagerFuture, shoppingFuture);

        try {
            allOf.join();
        } catch (CompletionException e) {
            throw (ApiException) e.getCause();
        }

        var voyagerJobResult = voyagerFuture.join();
        var products = shoppingFuture.join();

        return new ExperimentResult(voyagerJobResult.msg(), products);
    }
}
