package io.gridgo.utils.helper;

import java.util.concurrent.atomic.AtomicBoolean;

import io.gridgo.utils.ThreadUtils;

public abstract class AbstractStartable implements Startable {

	private final AtomicBoolean started = new AtomicBoolean(false);
	private volatile boolean running = false;

	@Override
	public final boolean isStarted() {
		ThreadUtils.busySpin(10, () -> {
			return this.started.get() ^ this.running;
		});
		return this.running;
	}

	@Override
	public final void start() {
		if (!this.isStarted() && started.compareAndSet(false, true)) {
			this.onStart();
			this.running = true;
		}
	}

	@Override
	public final void stop() {
		if (this.isStarted() && started.compareAndSet(true, false)) {
			try {
				this.onStop();
				this.running = false;
			} catch (Exception e) {
				this.started.set(true);
				throw e;
			}
		}
	}

	protected abstract void onStart();

	protected abstract void onStop();
}
