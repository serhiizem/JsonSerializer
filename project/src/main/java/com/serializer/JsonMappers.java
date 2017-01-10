package com.serializer;

import com.serializer.interfaces.JsonMapper;
import com.serializer.interfaces.JsonMapperFactory;

@SuppressWarnings("unchecked")
public class JsonMappers {

    public static final JsonMapper<Boolean> BOOLEAN = (value, jsonWriter) -> jsonWriter.write(value);
    public static final JsonMapper<Integer> INTEGER = (value, jsonWriter) -> jsonWriter.write(value);
    public static final JsonMapper<Double> DOUBLE = (value, jsonWriter) -> jsonWriter.write(value);
    public static final JsonMapper<Long> LONG = (value, jsonWriter) -> jsonWriter.write(value);
    public static final JsonMapper<String> STRING = (value, jsonWriter) -> jsonWriter.write(value);

    public static final JsonMapperFactory BOOLEAN_FACTORY = newFactory(Boolean.class, BOOLEAN);
    public static final JsonMapperFactory INTEGER_FACTORY = newFactory(Integer.class, INTEGER);
    public static final JsonMapperFactory DOUBLE_FACTORY = newFactory(Double.class, DOUBLE);
    public static final JsonMapperFactory LONG_FACTORY = newFactory(Long.class, LONG);
    public static final JsonMapperFactory STRING_FACTORY = newFactory(String.class, STRING);

    public static <TT> JsonMapperFactory newFactory(Class<TT> factoryType,
                                                    JsonMapper<TT> mapper) {
        return new JsonMapperFactory() {
            @Override
            public <T> JsonMapper<T> create(JsonSerializer serializer, Class<T> requestedType) {
                return factoryType.equals(requestedType) ? (JsonMapper<T>) mapper : null;
            }
        };
    }
}
