package com.serializer.mappers;

import com.serializer.JsonSerializer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NumberMapperTest {

    private JsonSerializer jsonSerializer;

    @Before
    public void setUp() throws Exception {
        jsonSerializer = new JsonSerializer();
    }

    @Test
    public void shouldSerializeAnyNumberDescendant() throws IOException {
        //given
        Number doulbeSample = 20d;
        String correctResult = "20.0";

        //when
        String serializedNumber = jsonSerializer.serialize(doulbeSample);

        //then
        assertThat(serializedNumber, is(correctResult));
    }
}
