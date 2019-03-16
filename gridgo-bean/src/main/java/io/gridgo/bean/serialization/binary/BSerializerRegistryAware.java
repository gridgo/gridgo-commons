package io.gridgo.bean.serialization.binary;

public interface BSerializerRegistryAware {

    void setSerializerRegistry(BSerializerRegistry serializerRegistry);

    BSerializerRegistry getSerializerRegistry();

    default BSerializer getSerializer() {
        return getSerializerRegistry().getDefault();
    }
}
