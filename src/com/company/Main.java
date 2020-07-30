package com.company;

import com.company.container.cargo.Cargo;
import com.company.port.Port;
import com.company.ship.exceptions.OverloadException;
import com.company.ship.route.Route;
import com.company.ship.Ship;
import com.company.container.Container;

import java.util.ArrayList;

public class Main {

    private static ArrayList<Container> createContainersWithCargosForPort(Port port)
    {
        // Создает контейнеры с грузом для заданного порта.
        ArrayList<Container> containers = new ArrayList<>();
        for (int i = 0; i < port.getCapacityLimit() / 2; i++) {
            Container container = new Container(15);
            try {
                container.load(new Cargo("Масло моторное", 200), 50);
            } catch (OverloadException e) {
                e.printStackTrace();
            }
            containers.add(container);
        }
        return containers;
    }

    public static void main(String[] args)
    {
        Port port1 = new Port("Черноморск", 5000, 3);
        Port port2 = new Port("Батуми", 8000, 4);

        // Заполняем ранее созданные порты контейнерами с грузом наполовину.
        port1.setContainers(createContainersWithCargosForPort(port1));
        port2.setContainers(createContainersWithCargosForPort(port2));

        // Создаем маршруты для кораблей.
        Route route1 = new Route(port2, Route.Target.LOAD);
        route1.addRoute(route1, new Route(port1, Route.Target.UNLOAD));

        Route route2 = new Route(port2, Route.Target.LOAD);
        route2.addRoute(route2, new Route(port1, Route.Target.UNLOAD));

        Route route3 = new Route(port2, Route.Target.LOAD);
        route3.addRoute(route3, new Route(port1, Route.Target.UNLOAD));

        Route route4 = new Route(port2, Route.Target.LOAD);
        route4.addRoute(route4, new Route(port1, Route.Target.UNLOAD));

        Route route5 = new Route(port2, Route.Target.LOAD);
        route5.addRoute(route5, new Route(port1, Route.Target.UNLOAD));

        Route route6 = new Route(port1, Route.Target.LOAD);
        route6.addRoute(route6, new Route(port2, Route.Target.UNLOAD));

        Route route7 = new Route(port1, Route.Target.LOAD);
        route7.addRoute(route7, new Route(port2, Route.Target.UNLOAD));

        Route route8 = new Route(port1, Route.Target.LOAD);
        route8.addRoute(route8, new Route(port2, Route.Target.UNLOAD));

        Route route9 = new Route(port1, Route.Target.LOAD);
        route9.addRoute(route9, new Route(port2, Route.Target.UNLOAD));

        Ship ship1 = new Ship("Volnorez", 550, route1);
        Ship ship2 = new Ship("King Trs", 420, route2);
        Ship ship3 = new Ship("Beremur", 675, route3);
        Ship ship4 = new Ship("Tredyteev", 500, route4);
        Ship ship5 = new Ship("Romakran", 455, route5);

        Ship ship6 = new Ship("Grivden", 525, route6);
        Ship ship7 = new Ship("Kosatka", 710, route7);
        Ship ship8 = new Ship("Velikiy", 400, route8);
        Ship ship9 = new Ship("Scadovsk", 560, route9);

        // Создаем потоки для каждого из портов.
        Thread threadPort1 = new Thread(port1);
        Thread threadPort2 = new Thread(port2);

        // Создаем потоки для каждого из кораблей.
        Thread threadShip1 = new Thread(ship1);
        Thread threadShip2 = new Thread(ship2);
        Thread threadShip3 = new Thread(ship3);
        Thread threadShip4 = new Thread(ship4);
        Thread threadShip5 = new Thread(ship5);
        Thread threadShip6 = new Thread(ship6);
        Thread threadShip7 = new Thread(ship7);
        Thread threadShip8 = new Thread(ship8);
        Thread threadShip9 = new Thread(ship9);

        threadShip1.start();
        threadShip2.start();
        threadShip3.start();
        threadShip4.start();
        threadShip5.start();
        threadShip6.start();
        threadShip7.start();
        threadShip8.start();
        threadShip9.start();

        threadPort1.start();
        threadPort2.start();
    }
}