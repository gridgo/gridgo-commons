package io.gridgo.framework.execution.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.gridgo.framework.execution.ExecutionContext;
import io.gridgo.framework.execution.ExecutionStrategy;
import lombok.NonNull;

public class ExecutorExecutionStrategy implements ExecutionStrategy {

	private boolean ownedExecutor;

	private ExecutorService executor;

	public ExecutorExecutionStrategy(final int noThreads) {
		this.executor = Executors.newFixedThreadPool(noThreads);
		this.ownedExecutor = true;
	}

	public ExecutorExecutionStrategy(final ExecutorService executor) {
		this.executor = executor;
	}

	@Override
	public void handle(final @NonNull ExecutionContext<?, ?> executionContext) {
		executor.submit(executionContext::execute);
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {
		if (ownedExecutor)
			executor.shutdown();
	}
}
