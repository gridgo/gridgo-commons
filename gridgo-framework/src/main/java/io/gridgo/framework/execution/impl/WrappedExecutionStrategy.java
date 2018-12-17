package io.gridgo.framework.execution.impl;

import io.gridgo.framework.execution.ExecutionStrategy;
import io.gridgo.framework.support.context.ExecutionContext;

public class WrappedExecutionStrategy implements ExecutionStrategy {

    private ExecutionStrategy strategy;

    public WrappedExecutionStrategy(ExecutionStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void execute(Runnable runnable) {
        this.strategy.execute(runnable);
    }

    @Override
    public void execute(ExecutionContext<?, ?> context) {
        this.strategy.execute(context);
    }
}
