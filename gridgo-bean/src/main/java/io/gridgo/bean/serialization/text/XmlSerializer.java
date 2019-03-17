package io.gridgo.bean.serialization.text;

import java.io.InputStream;
import java.io.OutputStream;

import io.gridgo.bean.BElement;
import io.gridgo.bean.serialization.AbstractBSerializer;
import io.gridgo.bean.serialization.BSerializationPlugin;

@BSerializationPlugin(XmlSerializer.NAME)
public class XmlSerializer extends AbstractBSerializer {

    public static final String NAME = "xml";

    @Override
    public void serialize(BElement element, OutputStream out) {
        element.writeXml(out, null);
    }

    @Override
    public BElement deserialize(InputStream in) {
        return this.getFactory().fromXml(in);
    }
}
