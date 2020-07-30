package com.company.container.cargo;

import java.util.Objects;

public final class Cargo {

    /* Класс описывает груз. */

    private final String name;
    private final double weight; // в кг.

    public Cargo(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public Cargo(Cargo other)
    {
        this(other.name, other.weight);
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cargo good = (Cargo) o;
        return Double.compare(good.weight, weight) == 0 &&
                Objects.equals(name, good.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight);
    }
}