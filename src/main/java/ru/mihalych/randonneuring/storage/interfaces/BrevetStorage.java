package ru.mihalych.randonneuring.storage.interfaces;

import ru.mihalych.randonneuring.model.Brevet;

import java.util.List;

public interface BrevetStorage {

    List<Brevet> getBrevets();

    Brevet getBrevet(Integer id);

    Brevet createBrevet(Brevet brevet);

    Brevet saveBrevet(Brevet brevet);

//    Integer deleteBrevet(Integer id);
}
