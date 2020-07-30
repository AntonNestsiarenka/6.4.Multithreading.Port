package com.company.port;

import com.company.ship.exceptions.OverloadException;
import com.company.ship.Ship;
import com.company.Utils.Utils;
import com.company.container.Container;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public final class Port implements Runnable {

    /* Класс описывает порт.
       Порт распределяет зашедшие в порт корабли по свободным причалам для загрузки/выгрузки контейнеров с грузом. */

    private String name;
    private final int capacityLimit; // вместимость по количеству контейнеров
    private final Berth[] berths;
    private ArrayList<Container> containers;
    private Queue<Ship> shipQueue;

    public Port(String name, int capacityLimit, int countOfBerths) {
        this.name = name;
        this.capacityLimit = capacityLimit;
        berths = new Berth[countOfBerths];
        for (int i = 0; i < berths.length; i++)
            berths[i] = new Berth(i + 1);
        containers = new ArrayList<>();
        shipQueue = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacityLimit() {
        return capacityLimit;
    }

    public Berth[] getBerths() {
        return berths;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public void setContainers(ArrayList<Container> containers) {
        if (containers.size() <= capacityLimit) {
            this.containers = containers;
        }
        else {
            ArrayList<Container> temp = new ArrayList<>();
            for (int i = 0; i < capacityLimit; i++)
                temp.add(containers.get(i));
            this.containers = temp;
        }
    }

    public Queue<Ship> getShipQueue() {
        return shipQueue;
    }

    public void setShipQueue(Queue<Ship> shipQueue) {
        this.shipQueue = shipQueue;
    }

    public void entry(Ship ship)
    {
        // Вход в порт.
        shipQueue.add(ship);
        ship.setCurrentPosition(this);
    }

    @Override
    public void run() {
        /* Метод запускает в работу потоки причалов данного порта, а так же распределяет зашедшие в порт корабли
           по свободным причалам для загрузки/выгрузки груза. */
        for (Berth berth : berths) {
            Thread thread = new Thread(berth);
            thread.start();
        }
        for (int i = 0; ; i = (i + 1) % berths.length)
        {
            if (!shipQueue.isEmpty() && berths[i].isSpare()) {
                berths[i].setShip(shipQueue.poll());
            }
            Utils.sleep(1000);
        }
    }

    class Berth implements Runnable {

        /* Класс описывает причал порта. Причал выполняет загрузку/разгрузку корабля. */

        private Ship ship;
        private final int number;

        public Berth(int number) {
            this.number = number;
        }

        public Ship getShip() {
            return ship;
        }

        public int getNumber() {
            return number;
        }

        public void setShip(Ship ship) {
            this.ship = ship;
        }

        @Override
        public String toString() {
            return "Berth{" +
                    "ship=" + ship +
                    ", number=" + number +
                    '}';
        }

        public boolean isSpare()
        {
            return ship == null;
        }

        synchronized private void loadShip()
        {
            // Загружает корабль контейнерами с грузом.
            boolean flag = true;
            System.out.println("Корабль " + ship.getName() + " загружается у причала №" + number + " в порту " + getName() +"...");
            while (true) {
                if (!containers.isEmpty()) {
                    Container container = containers.remove(0);
                    try {
                        Utils.sleep(Utils.randInt(500, 1000));
                        ship.load(container);
                    } catch (OverloadException e) {
                        break;
                    }
                }
                else {
                    flag = false;
                    break;
                }
            }
            if (flag)
                System.out.println("Корабль " + ship.getName() + " загружен полностью.");
            else {
                System.out.println("Корабль " + ship.getName() + " загружен не полностью. Порт " + getName() + " пуст.");
            }
        }

        synchronized private void unloadShip() {
            // Разгружает корабль.
            System.out.println("Корабль " + ship.getName() + " разгружается у причала №" + number + " в порту " + getName() +"...");
            Container container = null;
            boolean flag = true;
            while ((container = ship.unload()) != null) {
                Utils.sleep(Utils.randInt(500, 1000));
                if (containers.size() < capacityLimit) {
                    containers.add(container);
                } else {
                    flag = false;
                    try {
                        ship.load(container);
                    } catch (OverloadException e) {
                    }
                    break;
                }
            }
            if (flag)
                System.out.println("Корабль " + ship.getName() + " разгружен полностью.");
            else {
                System.out.println("Корабль " + ship.getName() + " разгрузился не полностью. Порт " + getName() + " заполнен.");
            }
        }

        @Override
        public void run() {
            // Метод загружает/разгружает корабль в зависимости от цели корабля.
            for (; ; ) {
                if (ship != null) {
                    switch (ship.getNextRoute().getTarget().getTarget().toLowerCase()) {
                        case ("load"): {
                            loadShip();
                            break;
                        }
                        case ("unload"): {
                            unloadShip();
                            break;
                        }
                    }
                    ship.setNextRoute(ship.getNextRoute().getNext());
                    ship.setCurrentPosition(null);
                    ship = null;
                }
                Utils.sleep(1000);
            }
        }
    }
}
