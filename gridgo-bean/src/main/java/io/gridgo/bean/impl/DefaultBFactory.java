package io.gridgo.bean.impl;

import java.util.function.Supplier;

import io.gridgo.bean.BArray;
import io.gridgo.bean.BFactory;
import io.gridgo.bean.BFactoryAware;
import io.gridgo.bean.BFactoryConfigurable;
import io.gridgo.bean.BObject;
import io.gridgo.bean.BReference;
import io.gridgo.bean.BValue;
import io.gridgo.bean.serialize.BSerializer;
import io.gridgo.bean.serialize.msgpack.MsgpackSerializer;
import io.gridgo.bean.xml.BXmlParser;
import lombok.Getter;

@Getter
public class DefaultBFactory implements BFactory, BFactoryConfigurable {

    private Supplier<BArray> arraySupplier = DefaultBArray::new;
    private Supplier<BObject> objectSupplier = DefaultBObject::new;
    private Supplier<BValue> valueSupplier = DefaultBValue::new;
    private Supplier<BReference> referenceSupplier = DefaultBReference::new;

    private BXmlParser xmlParser;
    private BSerializer serializer;

    public DefaultBFactory() {
        this.setSerializer(new MsgpackSerializer());
        this.setXmlParser(new BXmlParser());
    }

    @Override
    public BFactoryConfigurable setXmlParser(BXmlParser xmlParser) {
        this.xmlParser = xmlParser;
        if (this.xmlParser instanceof BFactoryAware) {
            this.xmlParser.setFactory(this);
        }
        return this;
    }

    @Override
    public BFactoryConfigurable setSerializer(BSerializer serializer) {
        this.serializer = serializer;
        if (this.serializer instanceof BFactoryAware) {
            ((BFactoryAware) this.serializer).setFactory(this);
        }
        return this;
    }

    @Override
    public BFactoryConfigurable setValueSupplier(Supplier<BValue> valueSupplier) {
        this.valueSupplier = valueSupplier;
        return this;
    }

    @Override
    public BFactoryConfigurable setObjectSupplier(Supplier<BObject> objectSupplier) {
        this.objectSupplier = objectSupplier;
        return this;
    }

    @Override
    public BFactoryConfigurable setArraySupplier(Supplier<BArray> arraySupplier) {
        this.arraySupplier = arraySupplier;
        return this;
    }

    @Override
    public BFactoryConfigurable setReferenceSupplier(Supplier<BReference> referenceSupplier) {
        this.referenceSupplier = referenceSupplier;
        return this;
    }

    @Override
    public BFactoryConfigurable asConfigurable() {
        return this;
    }
}
