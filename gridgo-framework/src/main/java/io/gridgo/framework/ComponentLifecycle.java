package io.gridgo.framework;

/**
 * Represents the lifecycle of a GridGo component. A component can be a
 * connector, a gateway or a configuration.
 */
public interface ComponentLifecycle {

    /**
     * Start the component
     */
    public default void start() {

    }

    /**
     * Stop the component
     */
    public default void stop() {

    }

    public String getName();
}
