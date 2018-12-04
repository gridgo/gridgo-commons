package io.gridgo.framework.support;

import io.gridgo.framework.support.exceptions.BeanNotFoundException;

public interface Registry {

	public Object lookup(String name);

	public default <T> T lookup(String name, Class<T> type) {
		Object answer = lookup(name);
		if (answer == null)
			return null;
		return type.cast(answer);
	}

	public Registry register(String name, Object answer);

	public default Object lookupMandatory(String name) {
		var answer = lookup(name);
		if (answer == null)
			throw new BeanNotFoundException("Bean " + name + " cannot be found using" + this.getClass().getName());
		return answer;
	}

	public default <T> T lookupMandatory(String name, Class<T> type) {
		var answer = lookup(name, type);
		if (answer == null)
			throw new BeanNotFoundException("Bean " + name + " cannot be found using" + this.getClass().getName());
		return answer;
	}
}
