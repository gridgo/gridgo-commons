package io.gridgo.framework.execution.impl;

import io.gridgo.framework.execution.ExecutionStrategy;
import io.gridgo.framework.support.context.ExecutionContext;
import lombok.NonNull;

public class DefaultExecutionStrategy implements ExecutionStrategy {

	public void execute(final @NonNull Runnable runnable) {
		runnable.run();
	}

	@Override
	public void execute(ExecutionContext<?, ?> context) {
		context.execute();
	}
}
