package io.gridgo.framework.execution;

import org.joo.promise4j.Promise;

public interface ExecutionContext<T, H> {

	public void execute();
	
	public Promise<H, Exception> promise();
	
	public T getRequest();
}
