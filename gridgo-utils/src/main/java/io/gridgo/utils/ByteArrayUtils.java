package io.gridgo.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import io.gridgo.utils.exception.UnsupportedTypeException;

public final class ByteArrayUtils {

    private ByteArrayUtils() {
        // private constructor
    }

    public static byte[] primitiveToBytes(Object data) {
        if (data != null) {
            if (data instanceof byte[]) {
                return (byte[]) data;
            } else if (data instanceof Boolean) {
                return new byte[] { (byte) (((Boolean) data) ? 1 : 0) };
            } else if (data instanceof String) {
                return ((String) data).getBytes();
            } else if (data instanceof BigDecimal) {
                BigInteger theInt = ((BigDecimal) data).unscaledValue();
                return theInt.toByteArray();
            } else if (data instanceof BigInteger) {
                return ((BigInteger) data).toByteArray();
            } else if (data instanceof Byte) {
                return new byte[] { (Byte) data };
            } else {
                ByteBuffer buffer = null;
                if (data instanceof Short) {
                    buffer = ByteBuffer.allocate(Short.BYTES);
                    buffer.putShort((Short) data);
                } else if (data instanceof Integer) {
                    buffer = ByteBuffer.allocate(Integer.BYTES);
                    buffer.putInt((Integer) data);
                } else if (data instanceof Float) {
                    buffer = ByteBuffer.allocate(Float.BYTES);
                    buffer.putFloat((Float) data);
                } else if (data instanceof Long) {
                    buffer = ByteBuffer.allocate(Long.BYTES);
                    buffer.putLong((Long) data);
                } else if (data instanceof Double) {
                    buffer = ByteBuffer.allocate(Double.BYTES);
                    buffer.putLong((Long) data);
                } else if (data instanceof Character) {
                    buffer = ByteBuffer.allocate(Character.BYTES);
                    buffer.putChar((Character) data);
                }

                if (buffer != null) {
                    return buffer.array();
                }
                throw new IllegalArgumentException("Data must be primitive type");
            }
        }
        return null;
    }

    public static final Number bytesToNumber(byte[] bytes, boolean isDecimal) {
        if (bytes != null) {
            if (bytes.length == 1) {
                return (byte) bytes[0];
            } else if (bytes.length < 4) {
                return ByteBuffer.wrap(bytes).getShort();
            } else if (bytes.length < 8) {
                if (isDecimal) {
                    return ByteBuffer.wrap(bytes).getFloat();
                }
                return ByteBuffer.wrap(bytes).getInt();
            } else if (bytes.length < 32) {
                if (isDecimal) {
                    return ByteBuffer.wrap(bytes).getDouble();
                }
                return ByteBuffer.wrap(bytes).getLong();
            } else {
                BigInteger bigInt = new BigInteger(bytes);
                if (isDecimal) {
                    return new BigDecimal(bigInt);
                }
                return bigInt;
            }
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    public static final <T> T bytesToPrimitive(Class<T> clazz, byte[] bytes) {
        if (clazz != null && bytes != null) {
            if (clazz == Boolean.class || clazz == Boolean.TYPE) {
                if (bytes.length == 0) {
                    return (T) Boolean.FALSE;
                } else {
                    for (byte b : bytes) {
                        if (b == 1) {
                            return (T) Boolean.TRUE;
                        }
                    }
                    return (T) Boolean.FALSE;
                }
            } else if (clazz == String.class) {
                return (T) new String(bytes);
            } else if (clazz == Character.class || clazz == Character.TYPE) {
                return (T) Character.valueOf(ByteBuffer.wrap(bytes).getChar());
            } else {
                Object result = null;
                if (clazz == Byte.class) {
                    result = bytes[0];
                } else if (clazz == Short.class) {
                    result = ByteBuffer.wrap(bytes).getShort();
                } else if (clazz == Integer.class) {
                    result = ByteBuffer.wrap(bytes).getInt();
                } else if (clazz == Long.class) {
                    result = ByteBuffer.wrap(bytes).getLong();
                } else if (clazz == BigInteger.class) {
                    result = new BigInteger(bytes);
                } else if (clazz == Float.class) {
                    result = ByteBuffer.wrap(bytes).getFloat();
                } else if (clazz == Double.class) {
                    result = ByteBuffer.wrap(bytes).getDouble();
                } else if (clazz == BigDecimal.class) {
                    result = new BigDecimal(new BigInteger(bytes));
                }

                if (result != null) {
                    return (T) result;
                }
            }
            throw new UnsupportedTypeException("Cannot convert bytes to primitive type " + clazz);
        }
        return null;
    }

    public static final String toHex(byte[] bytes, String prefix) {
        if (bytes != null) {
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
        return null;
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
                try {
                    os.write(bytes);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return os.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    public static byte[] fromHex(String hex) {
        int start = (hex.startsWith("0x") || hex.startsWith("0X")) ? 2 : 0;
        int len = hex.length() - start;
        byte[] data = new byte[len / 2];
        for (int i = start; i < hex.length(); i += 2) {
            data[(i - start) / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}
