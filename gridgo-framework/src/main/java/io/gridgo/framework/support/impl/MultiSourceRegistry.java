package io.gridgo.framework.support.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import io.gridgo.framework.support.Registry;
import lombok.NonNull;

public class MultiSourceRegistry implements Registry {

	private Registry[] registries;

	public MultiSourceRegistry(Registry... registries) {
		this.registries = registries;
	}

	public MultiSourceRegistry(@NonNull Collection<Registry> registries) {
		this.registries = registries.toArray(new Registry[0]);
	}

	@Override
	public Object lookup(String name) {
		return Arrays.stream(registries) //
				.map(registry -> registry.lookup(name)) //
				.filter(Objects::nonNull) //
				.findAny().orElse(null);
	}

	@Override
	public Registry register(String name, Object answer) {
		Arrays.stream(registries).forEach(registry -> tryRegister(registry, name, answer));
		return this;
	}

	private void tryRegister(Registry registry, String name, Object answer) {
		try {
			registry.register(name, answer);
		} catch (UnsupportedOperationException ex) {
			// do nothing
		}
	}
}
