package com.serializer;

import com.serializer.interfaces.JsonMapper;
import com.serializer.interfaces.JsonMapperFactory;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("unchecked")
public class ListMapper<E> implements JsonMapper<E> {

    public static final JsonMapperFactory LIST_MAPPER_FACTORY = new JsonMapperFactory() {

        @Override
        public <T> JsonMapper<T> create(JsonSerializer serializer, Class<T> type) {
            if(!(type.isAssignableFrom(ArrayList.class))) return null;
            Type genericSuperclass = type.getGenericSuperclass();
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            JsonMapper mapper = serializer.getMapper((Class<T>) typeArguments[0]);

            return new ListMapper<T>(mapper);
        }
    };

    private JsonMapper<E> mapper;

    public ListMapper(JsonMapper<E> mapper) {
        this.mapper = mapper;
    }

    @Override
    public void write(E value, JsonWriter jsonWriter) throws IOException {
        Collection<E> es = (Collection<E>) value;
        es.forEach(e -> this.writeValue(e, jsonWriter));
    }

    private void writeValue(E value, JsonWriter jsonWriter) {
        try {
            mapper.write(value, jsonWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
