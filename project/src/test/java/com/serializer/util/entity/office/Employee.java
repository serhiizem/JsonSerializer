package com.serializer.util.entity.office;

public class Employee extends Person {
    private Long id;
    private Integer hireYear;

    public Employee(Long id, String firstName, String lastName, Integer hireYear) {
        super(firstName, lastName);
        this.id = id;
        this.hireYear = hireYear;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", hireYear=" + hireYear +
                '}';
    }
}
