package ru.mihalych.randonneuring.storage;

import org.springframework.stereotype.Repository;
import ru.mihalych.randonneuring.model.Brevet;
import ru.mihalych.randonneuring.storage.interfaces.BrevetStorage;

import java.util.List;

@Repository
public class BrevetDBStorage implements BrevetStorage {

    @Override
    public List<Brevet> getBrevets() {
        System.out.println("getBrevets");
        return List.of(new Brevet());
    }

    @Override
    public Brevet getBrevet(Integer id) {
        System.out.println("getBrevet");
        return new Brevet();
    }

    @Override
    public Brevet createBrevet(Brevet brevet) {
        System.out.println("createBrevets");
        return new Brevet();
    }

    @Override
    public Brevet saveBrevet(Brevet brevet) {
        System.out.println("saveBrevets");
        return new Brevet();
    }
}
