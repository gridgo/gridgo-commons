package io.gridgo.framework.execution;

public interface ExecutionStrategyInstrumenter {

    public Runnable wrap(Runnable runnable);
}
