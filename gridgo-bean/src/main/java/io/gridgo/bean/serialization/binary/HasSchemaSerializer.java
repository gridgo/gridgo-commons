package io.gridgo.bean.serialization.binary;

public interface HasSchemaSerializer<S> {

    public void registerSchema(Class<? extends S> clazz, int id);

    public void deregisterSchema(Class<? extends S> clazz);

    public void deregisterSchema(int id);

    public Class<? extends S> lookupSchema(int id);

    public Integer lookupId(Class<?> clazz);
}
