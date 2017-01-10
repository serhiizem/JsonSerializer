package com.serializer;

import com.serializer.annotations.JsonIgnore;
import com.serializer.annotations.JsonProperty;
import com.serializer.interfaces.JsonMapper;
import com.serializer.interfaces.JsonMapperFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unchecked")
public final class ReflectionJsonFactory implements JsonMapperFactory {

    @Override
    public <T> JsonMapper<T> create(JsonSerializer jsonSerializer, Class<T> type) {
        return new PojoMapper(getFieldRepresentations(jsonSerializer, type));
    }

    private Map<String, FieldRepresentation> getFieldRepresentations(JsonSerializer jsonSerializer,
                                                                     Class<?> type) {
        Map<String, FieldRepresentation> result =
                new LinkedHashMap<>();

        while (type != Object.class) {
            Field[] fields = type.getDeclaredFields();
            for (Field field : fields) {
                if(!isIgnored(field)) {
                    field.setAccessible(true);
                    String fieldName = getFiledName(field);
                    Class<?> fieldType = field.getType();
                    FieldRepresentation boundField = createFieldRepresentation(jsonSerializer,
                            field, fieldName, fieldType);
                    result.put(fieldName, boundField);
                }
            }
            type = type.getSuperclass();
        }
        return result;
    }

    private boolean isIgnored(Field field) {
        return field.isAnnotationPresent(JsonIgnore.class);
    }

    private String getFiledName(Field field) {
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        Optional<JsonProperty> optionalProperty = Optional.ofNullable(jsonProperty);

        return optionalProperty.map(JsonProperty::name).orElse(field.getName());
    }

    private <T> FieldRepresentation createFieldRepresentation(JsonSerializer jsonSerializer,
                                                              final Field field, final String name, Class<T> type) {
        JsonMapper jsonMapper = jsonSerializer.getMapper(type);

        return new FieldRepresentation(name) {

            @Override
            void write(Object value, JsonWriter writer)
                    throws IOException, IllegalAccessException {
                Object fieldValue = field.get(value);
                jsonMapper.write(fieldValue, writer);
            }
        };
    }

    static abstract class FieldRepresentation {
        final String name;

        protected FieldRepresentation(String name) {
            this.name = name;
        }
        abstract void write(Object value, JsonWriter writer) throws IOException, IllegalAccessException;
    }

    public static final class PojoMapper<T> implements JsonMapper<T> {
        private final Map<String, FieldRepresentation> boundFields;

        PojoMapper(Map<String, FieldRepresentation> boundFields) {
            this.boundFields = boundFields;
        }

        @Override
        public void write(T value, JsonWriter jsonWriter) throws IOException {
            jsonWriter.beginObject();
            try {
                for (FieldRepresentation boundField : boundFields.values()) {
                    jsonWriter.name(boundField.name);
                    boundField.write(value, jsonWriter);
                }
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
            jsonWriter.endObject();
        }
    }
}