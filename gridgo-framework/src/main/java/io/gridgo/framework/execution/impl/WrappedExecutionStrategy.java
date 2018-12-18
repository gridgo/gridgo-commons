package io.gridgo.framework.execution.impl;

import io.gridgo.framework.execution.ExecutionStrategy;
import io.gridgo.framework.support.context.ExecutionContext;

public abstract class WrappedExecutionStrategy implements ExecutionStrategy {

    private static final ExecutionStrategy DEFAULT_EXECUTION_STRATEGY = new DefaultExecutionStrategy();

    private final ExecutionStrategy strategy;

    public WrappedExecutionStrategy() {
        this.strategy = DEFAULT_EXECUTION_STRATEGY;
    }

    public WrappedExecutionStrategy(ExecutionStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void execute(Runnable runnable) {
        this.strategy.execute(() -> wrap(runnable));
    }

    @Override
    public void execute(ExecutionContext<?, ?> context) {
        this.strategy.execute(context);
    }

    protected abstract void wrap(Runnable runnable);
}
