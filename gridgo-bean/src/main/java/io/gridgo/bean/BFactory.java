package io.gridgo.bean;

import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Supplier;

import io.gridgo.bean.exceptions.BeanSerializationException;
import io.gridgo.bean.exceptions.InvalidTypeException;
import io.gridgo.bean.impl.DefaultBFactory;
import io.gridgo.bean.serialize.BSerializer;
import io.gridgo.bean.xml.BXmlParser;
import io.gridgo.utils.ArrayUtils;
import io.gridgo.utils.PrimitiveUtils;
import lombok.NonNull;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

@SuppressWarnings("unchecked")
public interface BFactory {

    static final BFactory DEFAULT = new DefaultBFactory();

    static BFactory newInstance() {
        return new DefaultBFactory();
    }

    static BObject newDefaultObject() {
        return DEFAULT.newObject();
    }

    static BArray newDefaultArray() {
        return DEFAULT.newArray();
    }

    static BValue newDefaultValue() {
        return DEFAULT.newValue();
    }

    BXmlParser getXmlParser();

    BSerializer getSerializer();

    Supplier<BReference> getReferenceSupplier();

    Function<Map<String, BElement>, BObject> getObjectSupplier();

    Function<List<BElement>, BArray> getArraySupplier();

    Supplier<BValue> getValueSupplier();

    default BReference newReference(Object reference) {
        BReference bReference = newReference();
        bReference.setReference(reference);
        return bReference;
    }

    default BReference newReference() {
        return this.getReferenceSupplier().get();
    }

    default BObject newObject() {
        return newObjectWithHolder(new HashMap<>());
    }

    default BObject newObject(Object mapData) {
        if (mapData instanceof BObject) {
            return ((BObject) mapData);
        }

        Map<?, ?> map;
        if (Map.class.isAssignableFrom(mapData.getClass())) {
            map = (Map<?, ?>) mapData;
        } else if (mapData instanceof Properties) {
            var map1 = new HashMap<>();
            var props = (Properties) mapData;
            for (var entry : props.entrySet()) {
                map1.put(entry.getKey(), entry.getValue());
            }
            map = map1;
        } else {
            throw new InvalidTypeException("Cannot create new object from non-map data: " + mapData.getClass());
        }

        BObject result = newObject();
        result.putAnyAll(map);

        return result;
    }

    default BObject newObjectWithHolder(Map<String, BElement> holder) {
        BObject result = this.getObjectSupplier().apply(holder);
        result.setFactory(this);
        result.setSerializer(this.getSerializer());
        return result;
    }

    default BObject newObjectFromSequence(Object... elements) {
        if (elements != null && elements.length % 2 != 0) {
            throw new IllegalArgumentException("Sequence's length must be even");
        }

        BObject result = newObject();
        result.putAnySequence(elements);

        return result;
    }

    default BArray newArray() {
        return newArrayWithHolder(new ArrayList<>());
    }

    default BArray newArray(Object src) {
        if (src instanceof BArray) {
            return ((BArray) src);
        }

        BArray array = newArray();
        if (src != null && !ArrayUtils.isArrayOrCollection(src.getClass())) {
            array.addAny(src);
        } else {
            ArrayUtils.foreach(src, array::addAny);
        }

        return array;
    }

    default BArray newArrayFromSequence(Object... elements) {
        BArray result = this.newArray();
        result.addAnySequence(elements);
        return result;
    }

    default BArray newArrayWithHolder(List<BElement> holder) {
        BArray result = this.getArraySupplier().apply(holder);
        result.setFactory(this);
        result.setSerializer(this.getSerializer());
        return result;
    }

    default BValue newValue() {
        BValue result = this.getValueSupplier().get();
        result.setSerializer(this.getSerializer());
        return result;
    }

    default BValue newValue(Object data) {
        if (data instanceof BValue) {
            return ((BValue) data);
        }

        if (data != null && !(data instanceof byte[]) && !PrimitiveUtils.isPrimitive(data.getClass())) {
            throw new IllegalArgumentException("Cannot create new BValue from non-primitive data");
        }

        BValue result = newValue();
        result.setData(data);
        return result;
    }

    default <T extends BElement> T fromAny(Object obj) {
        if (obj instanceof BElement) {
            return (T) ((BElement) obj);
        } else if (obj == null || (obj instanceof byte[]) || PrimitiveUtils.isPrimitive(obj.getClass())) {
            return (T) newValue(obj);
        } else if (ArrayUtils.isArrayOrCollection(obj.getClass())) {
            return (T) newArray(obj);
        } else if (obj instanceof Map<?, ?> || obj instanceof Properties) {
            return (T) newObject(obj);
        }
        return (T) newReference(obj);
    }

    default <T extends BElement> T fromJson(@NonNull Reader reader) {
        try {
            return fromAny(new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(reader));
        } catch (ParseException e) {
            throw new BeanSerializationException("Cannot parse json from reader", e);
        }
    }

    default <T extends BElement> T fromJson(InputStream inputStream) {
        if (inputStream == null)
            return null;
        try {
            return fromAny(new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(inputStream));
        } catch (UnsupportedEncodingException | ParseException e) {
            throw new BeanSerializationException("Cannot parse json from input stream", e);
        }
    }

    default <T extends BElement> T fromJson(String json) {
        if (json == null)
            return null;
        try {
            return fromAny(new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(json));
        } catch (ParseException e) {
            return fromAny(json);
        }
    }

    default <T extends BElement> T fromXml(String xml) {
        return (T) this.getXmlParser().parse(xml);
    }

    default <T extends BElement> T fromBytes(InputStream in) {
        return (T) this.getSerializer().deserialize(in);
    }

    default <T extends BElement> T fromBytes(ByteBuffer buffer) {
        return (T) this.getSerializer().deserialize(buffer);
    }

    default <T extends BElement> T fromBytes(byte[] bytes) {
        return (T) this.getSerializer().deserialize(bytes);
    }

    default BFactoryConfigurable asConfigurable() {
        throw new UnsupportedOperationException(
                "Instance of " + this.getClass().getName() + " cannot be used as a " + BFactoryConfigurable.class.getSimpleName());
    }
}
