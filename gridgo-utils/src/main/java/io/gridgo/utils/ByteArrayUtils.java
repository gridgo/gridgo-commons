package io.gridgo.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import io.gridgo.utils.exception.RuntimeIOException;
import io.gridgo.utils.exception.UnsupportedTypeException;

public final class ByteArrayUtils {

    private ByteArrayUtils() {
        // private constructor
    }

    @SuppressWarnings("unchecked")
    public static final <T> T bytesToPrimitive(Class<T> clazz, byte[] bytes) {
        if (clazz == null || bytes == null)
            return null;
        if (clazz == Boolean.class || clazz == Boolean.TYPE)
            return (T) Boolean.valueOf(Long.valueOf(ByteBuffer.wrap(bytes).getLong()) != 0);
        if (clazz == Byte.class || clazz == Byte.TYPE)
            return (T) Byte.valueOf(bytes.length > 0 ? bytes[0] : 0);
        if (clazz == Short.class || clazz == Short.TYPE)
            return (T) Short.valueOf(ByteBuffer.wrap(bytes).getShort());
        if (clazz == Integer.class || clazz == Integer.TYPE)
            return (T) Integer.valueOf(ByteBuffer.wrap(bytes).getInt());
        if (clazz == Float.class || clazz == Float.TYPE)
            return (T) Float.valueOf(ByteBuffer.wrap(bytes).getFloat());
        if (clazz == Long.class || clazz == Long.TYPE)
            return (T) Long.valueOf(ByteBuffer.wrap(bytes).getLong());
        if (clazz == Double.class || clazz == Double.TYPE)
            return (T) Double.valueOf(ByteBuffer.wrap(bytes).getDouble());
        if (clazz == String.class)
            return (T) new String(bytes);
        if (clazz == Character.class || clazz == Character.TYPE)
            return (T) Character.valueOf(ByteBuffer.wrap(bytes).getChar());
        throw new UnsupportedTypeException();
    }

    public static final String toHex(byte[] bytes, String prefix) {
        if (bytes == null)
            return null;
        StringBuilder buffer = new StringBuilder();
        if (prefix != null) {
            buffer.append(prefix);
        }
        for (int i = 0; i < bytes.length; i++) {
            buffer.append(Character.forDigit((bytes[i] >> 4) & 0xF, 16));
            buffer.append(Character.forDigit((bytes[i] & 0xF), 16));
        }
        return buffer.toString();
    }

    public static final String toHex(byte[] bytes) {
        return toHex(bytes, null);
    }

    public static final byte[] concat(byte[]... bytesArray) {
        int length = 0;
        for (byte[] bytes : bytesArray) {
            if (bytes == null) {
                throw new NullPointerException("Byte array to be concated cannot be null");
            }
            length += bytes.length;
        }
        try (var os = new ByteArrayOutputStream(length)) {
            for (byte[] bytes : bytesArray) {
                concatBytes(os, bytes);
            }
            return os.toByteArray();
        } catch (IOException e) {
            return new byte[0];
        }
    }

    private static void concatBytes(ByteArrayOutputStream os, byte[] bytes) {
        try {
            os.write(bytes);
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    public static byte[] fromHex(String hex) {
        int start = (hex.startsWith("0x") || hex.startsWith("0X")) ? 2 : 0;
        int len = hex.length() - start;
        byte[] data = new byte[len / 2];
        for (int i = start; i < hex.length(); i += 2) {
            data[(i - start)
                    / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

}
