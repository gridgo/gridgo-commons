package io.gridgo.framework.execution;

import io.gridgo.framework.ComponentLifecycle;
import io.gridgo.framework.support.context.ExecutionContext;
import lombok.NonNull;

/**
 * Represents an execution strategy.
 *
 */
public interface ExecutionStrategy extends ComponentLifecycle {

	public void execute(final @NonNull Runnable runnable);
	
	public void execute(final @NonNull ExecutionContext<?, ?> context);
	
	public default String getName() {
		return null;
	}
}
