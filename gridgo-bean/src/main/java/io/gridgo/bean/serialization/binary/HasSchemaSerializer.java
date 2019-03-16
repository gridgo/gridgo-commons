package io.gridgo.bean.serialization.binary;

public interface HasSchemaSerializer {

    public void registerSchema(Class<?> clazz, int id);

    public void deregisterSchema(Class<?> clazz);

    public void deregisterSchema(int id);

    public Class<?> lookupSchema(int id);

    public Integer lookupId(Class<?> clazz);
}
