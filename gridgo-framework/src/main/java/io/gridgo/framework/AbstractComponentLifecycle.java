package io.gridgo.framework;

import java.util.concurrent.atomic.AtomicBoolean;

import io.gridgo.utils.ThreadUtils;
import io.gridgo.utils.helper.Loggable;
import io.gridgo.utils.helper.Startable;

public abstract class AbstractComponentLifecycle implements ComponentLifecycle, Loggable, Startable {

	private final AtomicBoolean started = new AtomicBoolean(false);

	private volatile boolean running = false;

	private String name;

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
			getLogger().trace("Component starting %s", getName());
			this.onStart();
			this.running = true;
			getLogger().trace("Component started %s", getName());
		}
	}

	@Override
	public final void stop() {
		if (this.isStarted() && started.compareAndSet(true, false)) {
			try {
				getLogger().trace("Component stopping %s", getName());
				this.onStop();
				this.running = false;
				getLogger().trace("Component stopped %s", getName());
			} catch (Exception e) {
				this.started.set(true);
				throw e;
			}
		}
	}

	@Override
	public String getName() {
		if (name == null)
			name = generateName();
		return name;
	}

	protected abstract String generateName();

	protected abstract void onStart();

	protected abstract void onStop();
}
