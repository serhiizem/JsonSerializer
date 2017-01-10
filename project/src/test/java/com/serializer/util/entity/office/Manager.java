package com.serializer.util.entity.office;

public class Manager extends Employee {
    private Employee deputy;

    public Manager(Employee deputy, Long id, String firstName, String lastName, Integer hireYear) {
        super(id, firstName, lastName, hireYear);
        this.deputy = deputy;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "deputy=" + deputy +
                '}';
    }
}
