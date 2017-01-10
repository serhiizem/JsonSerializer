package com.serializer.mappers;

import com.serializer.JsonSerializer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.serializer.util.TestUtil.TWENTY_AS_DOUBLE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DoubleMapperTest {

    private JsonSerializer jsonSerializer;

    @Before
    public void setUp() throws Exception {
        jsonSerializer = new JsonSerializer();
    }

    @Test
    public void shouldSerializeDouble() throws IOException {
        //given
        String correctResult = "20.0";

        //when
        String serializedDouble = jsonSerializer.serialize(TWENTY_AS_DOUBLE);

        //then
        assertThat(serializedDouble, is(correctResult));
    }
}
