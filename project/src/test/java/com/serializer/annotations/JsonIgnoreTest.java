package com.serializer.annotations;

import com.serializer.JsonSerializer;
import com.serializer.util.entity.store.Book;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class JsonIgnoreTest {

    private JsonSerializer jsonSerializer;

    @Before
    public void setUp() throws Exception {
        jsonSerializer = new JsonSerializer();
    }

    @Test
    public void shouldIgnoreProperty() throws Exception {
        //given
        String correctResult = "{\"numberOfPages\":159,\"title\":\"Fahrenheit 451\"}";
        Book book = new Book("Fahrenheit 451", 45d, 159);

        //when
        String serializedBook = jsonSerializer.serialize(book);

        //then
        assertThat(serializedBook, is(correctResult));
    }
}




