package com.serializer.interfaces;

import com.serializer.JsonWriter;

import java.io.IOException;

@FunctionalInterface
public interface JsonMapper<T> {

    void write(T value, JsonWriter jsonWriter) throws IOException;

}
