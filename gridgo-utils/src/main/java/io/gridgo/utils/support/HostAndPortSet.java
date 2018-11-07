package io.gridgo.utils.support;

import java.util.HashSet;

public class HostAndPortSet extends HashSet<HostAndPort> {

	private static final long serialVersionUID = 5876970618893492552L;

	public HostAndPortSet() {
		//
	}

	public HostAndPortSet(String multiValueCommaSeparated) {
		this.addAll(HostAndPort.parse(multiValueCommaSeparated));
	}

	public HostAndPortSet(HostAndPort... sources) {
		if (sources != null) {
			for (HostAndPort value : sources) {
				this.add(value);
			}
		}
	}

	public HostAndPort getFirst() {
		return this.iterator().next();
	}

	@Override
	public boolean equals(Object obj) {
		HostAndPortSet other = null;
		if (obj instanceof HostAndPortSet) {
			other = (HostAndPortSet) obj;
		} else if (obj instanceof String) {
			other = new HostAndPortSet((String) obj);
		} else if (obj instanceof HostAndPort) {
			if (this.size() == 1) {
				return this.getFirst().equals(obj);
			}
			return false;
		}

		if (other != null) {
			return this.equals(other);
		}

		return false;
	}
}
