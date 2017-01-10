package com.serializer.annotations;

import com.serializer.JsonSerializer;
import com.serializer.util.entity.vehicle.Truck;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class JsonPropertyAnnotationTest {

    private JsonSerializer jsonSerializer;

    @Before
    public void setUp() throws Exception {
        jsonSerializer = new JsonSerializer();
    }

    @Test
    public void shouldRenameProperty() throws Exception {
        //given
        String correctResult = "{\"load\":20000.0,\"year\":1990,\"manufactorer\":\"Subaru\"}";
        Truck truck = new Truck(1990, "Subaru", 20000d);

        //when
        String serializedTruck = jsonSerializer.serialize(truck);

        //then
        assertThat(serializedTruck, is(correctResult));
    }
}
