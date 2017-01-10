package com.serializer.util.entity.vehicle;

import com.serializer.annotations.JsonProperty;

public class Truck extends Vehicle {

    @JsonProperty(name = "load")
    private Double cargo;

    public Truck(Integer year, String manufactorer, Double cargo) {
        super(year, manufactorer);
        this.cargo = cargo;
    }

    public Double getCargo() {
        return cargo;
    }
}
