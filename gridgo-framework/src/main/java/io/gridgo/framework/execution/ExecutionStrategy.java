package io.gridgo.framework.execution;

import io.gridgo.framework.ComponentLifecycle;
import lombok.NonNull;

public interface ExecutionStrategy extends ComponentLifecycle {

	public void execute(final @NonNull Runnable runnable);
}
