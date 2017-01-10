package com.serializer.mappers;

import com.google.gson.Gson;
import com.serializer.JsonSerializer;
import com.serializer.util.entity.office.Employee;
import com.serializer.util.entity.office.Manager;
import com.serializer.util.entity.office.Person;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PojoMapperTest {

    private JsonSerializer jsonSerializer;
    private Gson gson;

    @Before
    public void setUp() throws Exception {
        this.jsonSerializer = new JsonSerializer();
        this.gson = new Gson();
    }

    @Test
    public void shouldSerializeComplexClasses() throws Exception {
        //given
        Employee dan = new Employee(1l, "Dan", "Banker", 1998);
        Manager manager = new Manager(dan, 2l, "Mark", "Seemann", 2000);
        String correctResult = gson.toJson(manager);

        //when
        String serializedManager = jsonSerializer.serialize(manager);

        //then
        assertThat(serializedManager, is(correctResult));
        System.out.println(serializedManager);
    }

    @Test
    public void shouldSerializeSimpleClasses() throws Exception {
        //given
        String correctResult = "{\"firstName\":\"Chris\",\"lastName\":\"Fehn\"}";
        Person person = new Person("Chris", "Fehn");

        //when
        String serializedPerson = jsonSerializer.serialize(person);

        //then
        assertThat(serializedPerson, is(correctResult));
    }
}
