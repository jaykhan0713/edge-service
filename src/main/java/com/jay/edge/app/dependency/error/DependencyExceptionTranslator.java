package com.jay.edge.app.dependency.error;

import java.util.function.Supplier;

import com.jay.edge.core.error.api.ApiException;
import com.jay.edge.core.error.api.ErrorType;
import com.jay.edge.core.error.dependency.DependencyCallException;

public final class DependencyExceptionTranslator {

    private DependencyExceptionTranslator() {}

    public static <T> T execute(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (DependencyCallException ex) {
            //FUTURE-NOTE: Map based on reason as reason enum grows.
            throw new ApiException(ErrorType.DEPENDENCY_UNAVAILABLE, ex);
        }
    }

    //used to wrap supplier around above execute, useful for passing to Futures
    public static <T> Supplier<T> wrapExecution(Supplier<T> supplier) {
        return () -> execute(supplier);
    }
}