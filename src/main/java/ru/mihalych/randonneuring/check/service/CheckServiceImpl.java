package ru.mihalych.randonneuring.check.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mihalych.randonneuring.check.model.Check;
import ru.mihalych.randonneuring.check.repository.CheckRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckServiceImpl implements CheckService {

    private final CheckRepository checkRepository;

    @Override
    @Transactional
    public Check saveCheck(Check check) {
        return checkRepository.save(check);
    }

    @Override
    public Check checkById(Integer id) {
        Optional<Check> checkOptional = checkRepository.findById(id);
        Check check = null;
        if (checkOptional.isPresent()) {
            check = checkOptional.get();
        }
        return check;
    }

    @Override
    public Integer maxKP(Integer userId) {
        return checkRepository.maxKP(userId);
    }
}
