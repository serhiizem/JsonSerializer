package com.serializer.interfaces;

import com.serializer.JsonSerializer;

public interface JsonMapperFactory {

    <T>JsonMapper<T> create(JsonSerializer serializer, Class<T> type);
}
