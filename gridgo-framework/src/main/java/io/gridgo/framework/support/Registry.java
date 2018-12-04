package io.gridgo.framework.support;

import java.util.regex.Pattern;

import io.gridgo.framework.support.exceptions.BeanNotFoundException;

public interface Registry {

	public static final Pattern REGISTRY_SUB_PATTERN = Pattern.compile("\\$\\{([^\\{]*)\\}");

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

	public default String substituteRegistries(String endpoint) {
		if (endpoint.indexOf('$') == -1)
			return endpoint;
		var matcher = REGISTRY_SUB_PATTERN.matcher(endpoint);
		if (!matcher.find())
			return endpoint;
		return matcher.replaceAll(result -> {
			var obj = lookup(result.group(1));
			return obj != null ? obj.toString() : "";
		});
	}
}
