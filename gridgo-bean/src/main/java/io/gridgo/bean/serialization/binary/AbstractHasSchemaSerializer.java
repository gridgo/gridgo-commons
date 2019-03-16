package io.gridgo.bean.serialization.binary;

import java.util.HashMap;
import java.util.Map;

import io.gridgo.bean.exceptions.SchemaInvalidException;
import lombok.NonNull;

public abstract class AbstractHasSchemaSerializer extends AbstractBSerializer implements HasSchemaSerializer {

    private final Map<Class<?>, Integer> classToId = new HashMap<>();
    private final Map<Integer, Class<?>> idToClass = new HashMap<>();

    private final Object registerLock = new Object();

    private final Class<?> checkedClass;

    protected AbstractHasSchemaSerializer(Class<?> checkedClass) {
        this.checkedClass = checkedClass;
    }

    protected AbstractHasSchemaSerializer() {
        this(null);
    }

    @Override
    public void registerSchema(@NonNull Class<?> clazz, int id) {
        if (this.checkedClass != null && !this.checkedClass.isAssignableFrom(clazz)) {
            throw new SchemaInvalidException("Cannot register schema of class " + clazz);
        }
        synchronized (registerLock) {
            if (!classToId.containsKey(clazz) && !idToClass.containsKey(id)) {
                classToId.put(clazz, id);
                idToClass.put(id, clazz);
                return;
            }
        }
        throw new SchemaInvalidException("Schema or id already registered: class=" + clazz + ", id=" + id);
    }

    @Override
    public void deregisterSchema(@NonNull Class<?> clazz) {
        if (classToId.containsKey(clazz)) {
            synchronized (registerLock) {
                if (classToId.containsKey(clazz)) {
                    var id = classToId.remove(clazz);
                    idToClass.remove(id);
                }
            }
        }
    }

    @Override
    public void deregisterSchema(int id) {
        if (idToClass.containsKey(id)) {
            synchronized (registerLock) {
                if (idToClass.containsKey(id)) {
                    var clazz = idToClass.remove(id);
                    classToId.remove(clazz);
                }
            }
        }
    }

    @Override
    public Integer lookupId(Class<?> clazz) {
        return this.classToId.get(clazz);
    }

    @Override
    public Class<?> lookupSchema(int id) {
        return this.idToClass.get(id);
    }
}
