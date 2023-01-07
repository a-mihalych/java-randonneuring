package ru.mihalych.randonneuring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mihalych.randonneuring.model.Brevet;
import ru.mihalych.randonneuring.storage.interfaces.BrevetStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrevetService {

    private final BrevetStorage brevetStorage;
    private final ValidationService validationService;

    public List<Brevet> brevets() {
        return brevetStorage.getBrevets();
    }

    public Brevet brevet(Integer id) {
        id = validationService.validationPositive(id);
        return brevetStorage.getBrevet(id);
    }

    public Brevet createBrevet(Brevet brevet) {
        validationService.validationPositive(brevet.getDistance());
        return brevetStorage.createBrevet(brevet);
    }

    public Brevet saveBrevet(Brevet brevet) {
        validationService.validationPositive(brevet.getDistance());
        validationService.validationPositive(brevet.getId());
        validationService.validationNotFoundBrevet(brevet.getId());
        return brevetStorage.saveBrevet(brevet);
    }
}
