package io.gridgo.bean;

import java.util.function.Supplier;

import io.gridgo.bean.serialize.BSerializer;
import io.gridgo.bean.xml.BXmlParser;

public interface BFactoryConfigurable {

    BFactoryConfigurable setValueSupplier(Supplier<BValue> valueSupplier);

    BFactoryConfigurable setObjectSupplier(Supplier<BObject> objectSupplier);

    BFactoryConfigurable setArraySupplier(Supplier<BArray> arraySupplier);

    BFactoryConfigurable setReferenceSupplier(Supplier<BReference> referenceSupplier);

    BFactoryConfigurable setXmlParser(BXmlParser parser);

    BFactoryConfigurable setSerializer(BSerializer serializer);
}