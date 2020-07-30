package com.company.ship.route;

import com.company.port.Port;

public final class Route {

    /* Класс описывает маршрут корабля. */

    private Port port;
    private Target target;
    private Route next;

    public Route(Port port, Target target) {
        this.port = port;
        this.target = target;
        next = this;
    }

    public Port getPort() {
        return port;
    }

    public Target getTarget() {
        return target;
    }

    public Route getNext() {
        return next;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public void setNext(Route next) {
        this.next = next;
    }

    public void addRoute(Route first, Route routeForAdd)
    {
        // Добавляет в конец маршрута новый маршрут.
        if (next != first)
            next.addRoute(first, routeForAdd);
        Route temp = next;
        next = routeForAdd;
        routeForAdd.next = temp;
    }

    @Override
    public String toString() {
        return "Route{" +
                "port=" + port.getName() +
                ", target=" + target +
                '}';
    }

    public void printInfo(Route first)
    {
        // Вывод всего маршрута.
        System.out.print(toString());
        if (next != first)
            next.printInfo(first);
    }

    public enum Target {

        LOAD("Load"),
        UNLOAD("Unload");

        private String target;

        Target(String target) {
            this.target = target;
        }

        public String getTarget() {
            return target;
        }

        @Override
        public String toString() {
            return target;
        }
    }
}