package com.company.ship;

import com.company.container.Container;
import com.company.port.Port;
import com.company.ship.exceptions.OverloadException;
import com.company.ship.interfaces.IShip;
import com.company.Utils.Utils;
import com.company.ship.route.Route;

import java.util.ArrayList;

public final class Ship implements IShip, Runnable {

    /* Класс описывает корабль.
       Корабли постоянно плавают по своим маршрутам между портами и выгружают/загружают контейнеры. */

    private String name;
    private final double carryingCapacity; // грузоподъемность в тоннах.
    private double currentWeightOfCargo; // текущий вес груза в тоннах.
    private ArrayList<Container> containers;
    private Route route;
    private Route nextRoute;
    private Port currentPosition;

    public Ship(String name, double carryingCapacity, Route route) {
        this.name = name;
        this.carryingCapacity = carryingCapacity;
        currentWeightOfCargo = 0;
        containers = new ArrayList<>();
        this.route = route;
        nextRoute = route;
    }

    public String getName() {
        return name;
    }

    public double getCarryingCapacity() {
        return carryingCapacity;
    }

    public double getCurrentWeightOfCargo() {
        return currentWeightOfCargo;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Route getNextRoute() {
        return nextRoute;
    }

    public void setNextRoute(Route currentPosition) {
        this.nextRoute = currentPosition;
    }

    public Port getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Port currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "name='" + name + '\'' +
                ", carryingCapacity=" + carryingCapacity +
                ", currentWeightOfCargo=" + currentWeightOfCargo +
                ", containers=" + containers +
                ", route=" + route +
                ", nextRoute=" + nextRoute +
                ", currentPosition=" + currentPosition +
                '}';
    }

    @Override
    public void load(Container container) throws OverloadException {
        // Погрузка контейнера на корабль.
        double tempWeight = currentWeightOfCargo + container.getCurrentWeight();
        if (tempWeight <= carryingCapacity) {
            containers.add(container);
            currentWeightOfCargo = tempWeight;
        }
        else {
            throw new OverloadException("It is impossible to load this container. The ship is fully loaded.");
        }
    }

    @Override
    public Container unload() {
        // Выгрузка контейнера из корабля.
        Container container = null;
        if (!containers.isEmpty()) {
            container = containers.remove(containers.size() - 1);
            currentWeightOfCargo -= container.getCurrentWeight();
        }
        return container;
    }

    @Override
    public void addRoute(Route route) {
        // Добавление в конец маршрута.
        this.route.addRoute(this.route, route);
    }

    @Override
    public void resetRoute() {
        // Сбрасывает маршрут.
        route.setNext(route);
    }

    @Override
    public void run() {
        // Метод управляет передвижением корабля по заданному маршруту.
        for (; ;) {
            if (currentPosition == null) {
                System.out.println("Корабль " + getName() + " направляется в порт " + nextRoute.getPort().getName() + "...");
                Utils.sleep(Utils.randInt(5000, 10000));
                nextRoute.getPort().entry(this);
                System.out.println("Корабль " + getName() + " заходит в порт " + currentPosition.getName());
            }
            Utils.sleep(1000);
        }
    }
}