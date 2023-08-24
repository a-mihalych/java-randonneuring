package ru.mihalych.randonneuring.check.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mihalych.randonneuring.check.model.Check;

public interface CheckRepository extends JpaRepository<Check, Integer> {

    @Query("select max(kp) " +
           "from Check " +
           "where user.id = ?1")
    Integer maxKP(Integer userId);
}
