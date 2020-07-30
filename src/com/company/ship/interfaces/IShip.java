package com.company.ship.interfaces;

import com.company.container.Container;
import com.company.ship.exceptions.OverloadException;
import com.company.ship.route.Route;

public interface IShip {

    /* Интерфейс корабля. */

    void load(Container container) throws OverloadException;
    Container unload();
    void addRoute(Route route);
    void resetRoute();
}
