package io.gridgo.framework.support;

public interface Registry {

	public Object lookup(String name);

	public <T> T lookup(String name, Class<T> type);
}
