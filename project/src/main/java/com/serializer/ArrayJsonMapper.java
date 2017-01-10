package com.serializer;

import com.serializer.interfaces.JsonMapper;
import com.serializer.interfaces.JsonMapperFactory;

import java.io.IOException;
import java.lang.reflect.Array;

@SuppressWarnings("unchecked")
public final class ArrayJsonMapper<E> implements JsonMapper<Object>{

    public static final JsonMapperFactory ARRAY_FACTORY = new JsonMapperFactory() {
        @Override
        public <T> JsonMapper<T> create(JsonSerializer serializer, Class<T> type) {
            if(!type.isArray()) return null;
            Class<?> componentType = type.getComponentType();
            JsonMapper<?> mapper = serializer.getMapper(componentType);
            return new ArrayJsonMapper(mapper);
        }
    };

    private JsonMapper<E> jsonMapper;

    public ArrayJsonMapper(JsonMapper<E> jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Override
    public void write(Object array, JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginArray();
        for(int i = 0; i < Array.getLength(array); i++) {
            E value = (E) Array.get(array, i);
            jsonMapper.write(value, jsonWriter);
        }
        jsonWriter.endArray();
    }

}
