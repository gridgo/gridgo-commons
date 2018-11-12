package io.gridgo.bean;

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

	public default String toJson() {
		return null;
	}

	public default <T> T toJsonElement() {
		return null;
	}

	public default String toXml(String name) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public default <T> T deepClone() {
		return (T) newDefault(this.getReference());
	}
}