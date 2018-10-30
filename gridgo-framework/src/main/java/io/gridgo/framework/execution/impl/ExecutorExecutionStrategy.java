package io.gridgo.framework.execution.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.gridgo.framework.execution.ExecutionStrategy;
import lombok.NonNull;

public class ExecutorExecutionStrategy implements ExecutionStrategy {

	private final boolean ownedExecutor;

	private final ExecutorService executor;

	public ExecutorExecutionStrategy(final int noThreads) {
		this.executor = Executors.newFixedThreadPool(noThreads);
		this.ownedExecutor = true;
	}

	public ExecutorExecutionStrategy(final @NonNull ExecutorService executor) {
		this.executor = executor;
		this.ownedExecutor = false;
	}

	@Override
	public void execute(final @NonNull Runnable runnable) {
		executor.submit(runnable);
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
