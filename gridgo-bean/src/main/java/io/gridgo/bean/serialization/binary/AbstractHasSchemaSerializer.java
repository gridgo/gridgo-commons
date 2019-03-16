package io.gridgo.bean.serialization.binary;

import java.util.HashMap;
import java.util.Map;

import io.gridgo.bean.exceptions.SchemaInvalidException;
import lombok.NonNull;

public abstract class AbstractHasSchemaSerializer extends AbstractBSerializer implements HasSchemaSerializer {

    private final Map<Class<?>, Integer> schemaToId = new HashMap<>();
    private final Map<Integer, Class<?>> idToSchema = new HashMap<>();

    private final Object registerLock = new Object();

    private final Class<?> abstractSchema;

    protected AbstractHasSchemaSerializer(Class<?> checkedClass) {
        this.abstractSchema = checkedClass;
    }

    protected AbstractHasSchemaSerializer() {
        this(null);
    }

    @Override
    public void registerSchema(@NonNull Class<?> schema, int id) {
        if (this.abstractSchema != null && !this.abstractSchema.isAssignableFrom(schema)) {
            throw new SchemaInvalidException("Cannot register schema of class " + schema);
        }
        synchronized (registerLock) {
            if (!schemaToId.containsKey(schema) && !idToSchema.containsKey(id)) {
                schemaToId.put(schema, id);
                idToSchema.put(id, schema);
                onRegisterSchema(schema, id);
                return;
            }
        }
        throw new SchemaInvalidException("Schema or id already registered: class=" + schema + ", id=" + id);
    }

    @Override
    public void deregisterSchema(@NonNull Class<?> schema) {
        if (schemaToId.containsKey(schema)) {
            synchronized (registerLock) {
                if (schemaToId.containsKey(schema)) {
                    var id = schemaToId.remove(schema);
                    idToSchema.remove(id);
                    this.onDeregisterSchema(schema, id);
                }
            }
        }
    }

    @Override
    public void deregisterSchema(int id) {
        if (idToSchema.containsKey(id)) {
            synchronized (registerLock) {
                if (idToSchema.containsKey(id)) {
                    var schema = idToSchema.remove(id);
                    schemaToId.remove(schema);
                    this.onDeregisterSchema(schema, id);
                }
            }
        }
    }

    @Override
    public Integer lookupId(Class<?> schema) {
        return this.schemaToId.get(schema);
    }

    @Override
    public Class<?> lookupSchema(int id) {
        return this.idToSchema.get(id);
    }

    protected void onRegisterSchema(Class<?> schema, int id) {
        // do nothing
    }

    protected void onDeregisterSchema(Class<?> schema, int id) {
        // do nothing
    }
}
