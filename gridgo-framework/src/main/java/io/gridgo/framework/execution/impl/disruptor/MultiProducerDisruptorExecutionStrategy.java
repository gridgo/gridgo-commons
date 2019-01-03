package io.gridgo.framework.execution.impl.disruptor;

import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import io.gridgo.framework.execution.ExecutionStrategy;
import io.gridgo.framework.execution.impl.ExecutionContextEvent;
import io.gridgo.framework.support.context.ExecutionContext;
import io.gridgo.framework.support.context.impl.DefaultExecutionContext;

public class MultiProducerDisruptorExecutionStrategy<T, H> implements ExecutionStrategy {

    private static final int DEFAULT_BUFFER_SIZE = 1024;

    private Disruptor<ExecutionContextEvent<T, H>> disruptor;

    public MultiProducerDisruptorExecutionStrategy() {
        this(DEFAULT_BUFFER_SIZE);
    }

    public MultiProducerDisruptorExecutionStrategy(final int bufferSize) {
        this(bufferSize, new BlockingWaitStrategy());
    }

    public MultiProducerDisruptorExecutionStrategy(final int bufferSize, final WaitStrategy waitStrategy) {
        this(bufferSize, waitStrategy, (runnable) -> {
            return new Thread(runnable);
        });
    }

    public MultiProducerDisruptorExecutionStrategy(final int bufferSize, final WaitStrategy waitStrategy,
            final ThreadFactory threadFactory) {
        this.disruptor = new Disruptor<>(ExecutionContextEvent::new, bufferSize, threadFactory, ProducerType.MULTI,
                waitStrategy);
        this.disruptor.handleEventsWith(this::onEvent);
    }

    private void onEvent(ExecutionContextEvent<?, ?> event, long sequence, boolean endOfBatch) {
        event.getContext().execute();
    }

    @Override
    public void execute(Runnable runnable) {
        ExecutionContext<T, H> context = new DefaultExecutionContext<>(t -> runnable.run());
        execute(context);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void execute(ExecutionContext context) {
        this.disruptor.publishEvent((event, sequence) -> {
            event.clear();
            event.setContext(context);
        });
    }

    @Override
    public void start() {
        disruptor.start();
    }

    @Override
    public void stop() {
        disruptor.shutdown();
    }
}
