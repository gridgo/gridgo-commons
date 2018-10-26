package io.gridgo.framework.execution.impl;

import io.gridgo.framework.execution.ExecutionContext;
import io.gridgo.framework.execution.ExecutionStrategy;
import lombok.NonNull;

public class DefaultExecutionStrategy implements ExecutionStrategy {

	@SuppressWarnings("rawtypes")
	public void handle(final @NonNull ExecutionContext executionContext) {
		executionContext.execute();
	}
}
