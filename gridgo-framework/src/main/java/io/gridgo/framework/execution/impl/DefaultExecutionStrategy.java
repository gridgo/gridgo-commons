package io.gridgo.framework.execution.impl;

import io.gridgo.framework.execution.ExecutionStrategy;
import lombok.NonNull;

public class DefaultExecutionStrategy implements ExecutionStrategy {

	public void execute(final @NonNull Runnable runnable) {
		runnable.run();
	}
}
