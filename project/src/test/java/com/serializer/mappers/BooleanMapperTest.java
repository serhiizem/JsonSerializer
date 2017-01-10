package com.serializer.mappers;

import com.serializer.JsonSerializer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BooleanMapperTest {

    private JsonSerializer jsonSerializer;

    @Before
    public void setUp() throws Exception {
        jsonSerializer = new JsonSerializer();
    }

    @Test
    public void shouldSerializeTrue() throws IOException {
        //given
        String correctResult = "true";

        //when
        String serializedTrue = jsonSerializer.serialize(Boolean.TRUE);

        //then
        assertThat(serializedTrue, is(correctResult));
    }

    @Test
    public void shouldSerializeFalse() throws Exception {
        //given
        String correctResult = "false";

        //when
        String serializedTrue = jsonSerializer.serialize(Boolean.FALSE);

        //then
        assertThat(serializedTrue, is(correctResult));
    }
}