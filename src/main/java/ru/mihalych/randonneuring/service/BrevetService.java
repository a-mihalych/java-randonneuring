package ru.mihalych.randonneuring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mihalych.randonneuring.model.Brevet;
import ru.mihalych.randonneuring.storage.interfaces.BrevetStorage;

import java.util.List;

@Service
public class BrevetService {

    BrevetStorage brevetStorage;

    @Autowired
    public BrevetService(BrevetStorage brevetStorage) {
        this.brevetStorage = brevetStorage;
    }

    public List<Brevet> brevets() {
        return brevetStorage.getBrevets();
    }

    public Brevet brevet(Integer id) {
        return brevetStorage.getBrevet(id);
    }

    public Brevet createBrevet(Brevet brevet) {
        return brevetStorage.createBrevet(brevet);
    }

    public Brevet caveBrevet(Brevet brevet) {
        return brevetStorage.saveBrevet(brevet);
    }
}
