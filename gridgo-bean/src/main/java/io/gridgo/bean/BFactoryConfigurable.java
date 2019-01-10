package io.gridgo.bean;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import io.gridgo.bean.serialize.BSerializer;
import io.gridgo.bean.xml.BXmlParser;

public interface BFactoryConfigurable {

    BFactoryConfigurable setValueSupplier(Supplier<BValue> valueSupplier);

    BFactoryConfigurable setObjectSupplier(Function<Map<String, BElement>, BObject> objectSupplier);

    BFactoryConfigurable setWrappedObjectSupplier(Function<Map<?, ?>, BObject> wrappedObjectSupplier);

    BFactoryConfigurable setArraySupplier(Function<List<BElement>, BArray> arraySupplier);

    BFactoryConfigurable setWrappedArraySupplier(Function<Collection<?>, BArray> wrappedArraySupplier);

    BFactoryConfigurable setReferenceSupplier(Supplier<BReference> referenceSupplier);

    BFactoryConfigurable setXmlParser(BXmlParser parser);

    BFactoryConfigurable setSerializer(BSerializer serializer);
}