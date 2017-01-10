package com.serializer.mappers;

import com.serializer.JsonSerializer;
import org.junit.Before;
import org.junit.Test;

import static com.serializer.util.TestUtil.TWENTY_AS_STRING;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class StringMapperTest {

    private JsonSerializer jsonSerializer;

    @Before
    public void setUp() throws Exception {
        jsonSerializer = new JsonSerializer();
    }

    @Test
    public void shouldSerializeString() throws Exception {
        //given
        String correctResult = "\"twenty\"";

        //when
        String serializedString = jsonSerializer.serialize(TWENTY_AS_STRING);

        //then
        assertThat(serializedString, is(correctResult));
    }
}