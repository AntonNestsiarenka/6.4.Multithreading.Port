package com.company.container.interfaces;

import com.company.container.cargo.Cargo;
import com.company.ship.exceptions.OverloadException;

import java.util.ArrayList;

public interface IContainer {

    /* Интерфейс для работы с контейнером. */

    void load(Cargo cargo, int count) throws OverloadException;
    ArrayList<Cargo> unload();
}
