package io.gridgo.bean;

import java.util.function.Supplier;

import io.gridgo.bean.serialize.BSerializer;
import io.gridgo.bean.xml.BXmlParser;

public interface BFactoryConfigurable {

    void setValueSupplier(Supplier<BValue> valueSupplier);

    void setObjectSupplier(Supplier<BObject> objectSupplier);

    void setArraySupplier(Supplier<BArray> arraySupplier);

    void setXmlParser(BXmlParser parser);

    void setSerializer(BSerializer serializer);
}