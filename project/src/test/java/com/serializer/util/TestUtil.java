package com.serializer.util;

import com.serializer.util.entity.office.Person;

public class TestUtil {

    public static final Integer TWENTY_AS_INTEGER = 20;
    public static final Double TWENTY_AS_DOUBLE = 20d;
    public static final Long TWENTY_AS_LONG = 20l;
    public static final String TWENTY_AS_STRING = "twenty";

    public static final Person[] SAMPLE_ARRAY_OF_PEOPLE = getSampleArrayOfPeople();

    private static Person[] getSampleArrayOfPeople() {
        Person[] people = new Person[3];
        Person shawn = new Person("Shawn", "Rait");
        Person mick = new Person("Mick", "Newton");
        Person chris = new Person("Chris", "Santoro");

        people[0] = shawn;
        people[1] = mick;
        people[2] = chris;

        return people;
    }
}
