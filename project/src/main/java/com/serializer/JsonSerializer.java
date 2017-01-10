package com.serializer;

import com.serializer.interfaces.JsonMapper;
import com.serializer.interfaces.JsonMapperFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.serializer.ArrayJsonMapper.ARRAY_FACTORY;
import static com.serializer.JsonMappers.BOOLEAN_FACTORY;
import static com.serializer.JsonMappers.DOUBLE_FACTORY;
import static com.serializer.JsonMappers.INTEGER_FACTORY;
import static com.serializer.JsonMappers.LONG_FACTORY;
import static com.serializer.JsonMappers.STRING_FACTORY;
import static com.serializer.ListMapper.LIST_MAPPER_FACTORY;

@SuppressWarnings("unchecked")
public class JsonSerializer {

    private List<JsonMapperFactory> factories = new ArrayList<>();
    private StringWriter stringWriter = new StringWriter();

    private Map<Class, JsonMapper> mappersChache = new ConcurrentHashMap<>();

    public JsonSerializer() {
        factories.add(STRING_FACTORY);
        factories.add(BOOLEAN_FACTORY);
        factories.add(INTEGER_FACTORY);
        factories.add(DOUBLE_FACTORY);
        factories.add(LONG_FACTORY);
        factories.add(ARRAY_FACTORY);
        factories.add(LIST_MAPPER_FACTORY);
        factories.add(new ReflectionJsonFactory());
    }

    public String serialize(Object obj) throws IOException {
        JsonWriter jsonWriter = new JsonWriter(stringWriter);
        return serialize(obj, jsonWriter);
    }

    private String serialize(Object obj, JsonWriter jsonWriter) throws IOException {
        JsonMapper<?> mapper = this.getMapper(obj.getClass());
        ((JsonMapper<Object>) mapper).write(obj, jsonWriter);
        return jsonWriter.toString();
    }

    /**
     * Exctracts a required {@link JsonMapper<T>} object from cache.
     * If yet there is not such mapper in cache, starts to look over the
     * availible mappers defined in {@link JsonMappers} class.
     *
     * @param type {@code Class} parametrized over the type <T> that will
     *             be used to map a type of the object which is about to be
     *             serialized to the corresponding {@link JsonMapper} object
     * @param <T>  a bound which ensures, that a {@link JsonMapper} object
     *             exctracted from the factory corresponds to the type passed
     *             to this method as a parameter
     * @return corresponding {@link JsonMapper} object
     */
    public <T> JsonMapper<T> getMapper(Class<T> type) {
        JsonMapper<?> cachedMapper = mappersChache.get(type);
        if(cachedMapper != null) {
            return (JsonMapper<T>) cachedMapper;
        }

        for (JsonMapperFactory factory : factories) {
            JsonMapper<T> candidate = factory.create(this, type);
            if(candidate != null) {
                mappersChache.put(type, candidate);
                return candidate;
            }
        }
        throw new IllegalArgumentException("Serializer cannot handle " + type);
    }
}