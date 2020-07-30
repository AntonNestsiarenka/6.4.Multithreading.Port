package com.company.container;

import com.company.container.cargo.Cargo;
import com.company.container.interfaces.IContainer;
import com.company.ship.exceptions.OverloadException;

import java.util.ArrayList;

public final class Container implements IContainer {

    /* Класс описывает контейнер для перевозки грузов. */

    private static final double COEFFICIENT_OF_CONTAINER_WEIGHT = 0.07;

    private final double maxWeightOfCargo; // максимальный вес груза контейнера в тоннах.
    private double currentWeight; // текущий вес контейнера вместе с грузом в тоннах.
    private ArrayList<Cargo> cargos;

    public Container(double maxWeightOfCargo) {
        this.maxWeightOfCargo = maxWeightOfCargo;
        currentWeight = COEFFICIENT_OF_CONTAINER_WEIGHT * maxWeightOfCargo;
        cargos = new ArrayList<>();
    }

    public double getMaxWeightOfCargo() {
        return maxWeightOfCargo;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public ArrayList<Cargo> getCargos() {
        return cargos;
    }

    @Override
    public String toString() {
        return "Container{" +
                "maxWeightOfCargo=" + maxWeightOfCargo +
                ", currentWeight=" + currentWeight +
                ", cargos=" + cargos +
                '}';
    }

    @Override
    public void load(Cargo cargo, int count) throws OverloadException {
        // Загрузка груза в контейнер.
        for (int i = 0; i < count; i++) {
            Cargo copyCargo = new Cargo(cargo);
            if (copyCargo.getWeight() / 1000 + currentWeight <= maxWeightOfCargo) {
                currentWeight += copyCargo.getWeight() / 1000;
                cargos.add(copyCargo);
            }
            else {
                throw new OverloadException("It is impossible to load this good. The container is fully loaded.");
            }
        }
    }

    @Override
    public ArrayList<Cargo> unload() {
        // Выгрузка груза из контейнера.
        ArrayList<Cargo> goods1 = getCargos();
        cargos = new ArrayList<>();
        currentWeight = COEFFICIENT_OF_CONTAINER_WEIGHT * maxWeightOfCargo;
        return goods1;
    }
}