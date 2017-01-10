package com.serializer.mappers;

import com.serializer.JsonSerializer;
import org.junit.Before;
import org.junit.Test;

import static com.serializer.util.TestUtil.TWENTY_AS_INTEGER;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IntegerMapperTest {
    private JsonSerializer jsonSerializer;

    @Before
    public void setUp() throws Exception {
        jsonSerializer = new JsonSerializer();
    }

    @Test
    public void shouldSerializeInteger() throws Exception {
        //given
        String correctResult = "20";

        //when
        String serializedLong = jsonSerializer.serialize(TWENTY_AS_INTEGER);

        //then
        assertThat(serializedLong, is(correctResult));
    }
}
