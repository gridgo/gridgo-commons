package io.gridgo.bean;

import java.io.IOException;

public interface BReference extends BElement {

	static BReference newDefault(Object reference) {
		return BFactory.DEFAULT.newReference(reference);
	}

	@Override
	default BType getType() {
		return BType.REFERENCE;
	}

	public Object getReference();

	public void setReference(Object reference);

	public default void writeString(String name, int numTab, StringBuilder writer) {

	}

	@Override
	default void writeJson(Appendable out) {
		try {
			out.append(this.toJson());
		} catch (IOException e) {
			throw new RuntimeException("Error while writing json", e);
		}
	}

	public default String toJson() {
		var ref = getReference();
		if (ref != null && ref instanceof SerializableReference)
			return ((SerializableReference) ref).toJson();
		return null;
	}

	@SuppressWarnings("unchecked")
	public default <T> T toJsonElement() {
		var ref = getReference();
		if (ref != null && ref instanceof SerializableReference)
			return (T) ((SerializableReference) ref).toJsonElement();
		return null;
	}

	public default String toXml(String name) {
		var ref = getReference();
		if (ref != null && ref instanceof SerializableReference)
			return ((SerializableReference) ref).toXml();
		return null;
	}

	@SuppressWarnings("unchecked")
	public default <T> T deepClone() {
		return (T) newDefault(this.getReference());
	}
}
