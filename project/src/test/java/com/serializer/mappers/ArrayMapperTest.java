package com.serializer.mappers;

import com.google.gson.Gson;
import com.serializer.JsonSerializer;
import org.junit.Before;
import org.junit.Test;

import static com.serializer.util.TestUtil.SAMPLE_ARRAY_OF_PEOPLE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ArrayMapperTest {
    private JsonSerializer jsonSerializer;
    private Gson gson;

    @Before
    public void setUp() throws Exception {
        jsonSerializer = new JsonSerializer();
        this.gson = new Gson();
    }

    @Test
    public void shouldSerializeArrayOfPrimitives() throws Exception {
        //given
        Double[] arrayOfDoubles = new Double[] {12.6, 18.3, 15.5, 10.2};
        String correctResult = "[12.6,18.3,15.5,10.2]";

        //when
        String serializedArrayOfDoubles = jsonSerializer.serialize(arrayOfDoubles);

        //then
        assertThat(serializedArrayOfDoubles, is(correctResult));
    }

    @Test
    public void shouldSerializeArrayOfPojos() throws Exception {
        //given
        String correctResult = "[{\"firstName\":\"Shawn\",\"lastName\":\"Rait\"}," +
                "{\"firstName\":\"Mick\",\"lastName\":\"Newton\"}," +
                "{\"firstName\":\"Chris\",\"lastName\":\"Santoro\"}]";

        //when
        String serializedArrayOfPeople = jsonSerializer.serialize(SAMPLE_ARRAY_OF_PEOPLE);

        //then
        assertThat(serializedArrayOfPeople, is(correctResult));
    }
}
