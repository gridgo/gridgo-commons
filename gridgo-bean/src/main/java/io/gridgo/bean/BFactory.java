package io.gridgo.bean;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

import io.gridgo.bean.exceptions.InvalidTypeException;
import io.gridgo.bean.impl.DefaultBFactory;
import io.gridgo.bean.serialize.BSerializer;
import io.gridgo.bean.serialize.BSerializerAware;
import io.gridgo.bean.xml.BXmlParser;
import io.gridgo.utils.ArrayUtils;
import io.gridgo.utils.PrimitiveUtils;
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

    Supplier<BObject> getObjectSupplier();

    Supplier<BArray> getArraySupplier();

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
        BObject result = this.getObjectSupplier().get();
        result.setFactory(this);
        if (result instanceof BSerializerAware) {
            ((BSerializerAware) result).setSerializer(this.getSerializer());
        }
        return result;
    }

    default BObject newObject(Object mapData) {
        if (mapData instanceof BObject) {
            return ((BObject) mapData).deepClone();
        }

        Map<?, ?> map;
        if (Map.class.isAssignableFrom(mapData.getClass())) {
            map = (Map<?, ?>) mapData;
        } else if (mapData instanceof Properties) {
            Map<Object, Object> map1 = new HashMap<>();
            Properties props = (Properties) mapData;
            for (Object key : props.keySet()) {
                map1.put(key, props.get(key));
            }
            map = map1;
        } else {
            throw new InvalidTypeException("Cannot create new object from non-map data: " + mapData.getClass());
        }

        BObject result = newObject();
        result.putAnyAll(map);

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
        BArray result = this.getArraySupplier().get();
        result.setFactory(this);
        if (result instanceof BSerializerAware) {
            ((BSerializerAware) result).setSerializer(this.getSerializer());
        }
        return result;
    }

    default BArray newArray(Object src) {
        if (src instanceof BArray) {
            return ((BArray) src).deepClone();
        }

        BArray array = newArray();
        if (src != null && !ArrayUtils.isArrayOrCollection(src.getClass())) {
            array.addAny(src);
        } else {
            ArrayUtils.foreach(src, (entry) -> {
                array.addAny(entry);
            });
        }

        return array;
    }

    default BArray newArrayFromSequence(Object... elements) {
        BArray result = this.newArray();
        result.addAnySequence(elements);
        return result;
    }

    default BValue newValue() {
        BValue result = this.getValueSupplier().get();
        if (result instanceof BSerializerAware) {
            ((BSerializerAware) result).setSerializer(this.getSerializer());
        }
        return result;
    }

    default BValue newValue(Object data) {
        if (data instanceof BValue) {
            return ((BValue) data).deepClone();
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
            return (T) ((BElement) obj).deepClone();
        } else if (obj == null || (obj instanceof byte[]) || PrimitiveUtils.isPrimitive(obj.getClass())) {
            return (T) newValue(obj);
        } else if (ArrayUtils.isArrayOrCollection(obj.getClass())) {
            return (T) newArray(obj);
        } else if (obj instanceof Map<?, ?> || obj instanceof Properties) {
            return (T) newObject(obj);
        }
        return (T) newReference(obj);
    }

    default <T extends BElement> T fromJson(InputStream inputStream) {
        if (inputStream != null) {
            try {
                return (T) fromAny(new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(inputStream));
            } catch (UnsupportedEncodingException | ParseException e) {
                throw new RuntimeException("Cannot parse json from input stream", e);
            }
        }
        return null;
    }

    default <T extends BElement> T fromJson(String json) {
        if (json != null) {
            try {
                return (T) fromAny(new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(json));
            } catch (ParseException e) {
                return (T) fromAny(json);
            }
        }
        return null;
    }

    default <T extends BElement> T fromXml(String xml) {
        return (T) this.getXmlParser().parse(xml);
    }

    default <T extends BElement> T fromRaw(InputStream in) {
        return (T) this.getSerializer().deserialize(in);
    }

    default <T extends BElement> T fromRaw(ByteBuffer buffer) {
        return (T) this.getSerializer().deserialize(buffer);
    }

    default <T extends BElement> T fromRaw(byte[] bytes) {
        return (T) this.getSerializer().deserialize(bytes);
    }

    default BFactoryConfigurable asConfigurableFactory() {
        return (BFactoryConfigurable) this;
    }
}
