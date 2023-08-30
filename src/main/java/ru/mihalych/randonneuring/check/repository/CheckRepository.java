package ru.mihalych.randonneuring.check.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mihalych.randonneuring.check.model.Check;

import java.util.List;

public interface CheckRepository extends JpaRepository<Check, Integer> {

    @Query("select max(kp) " +
           "from Check " +
           "where user.id = ?1")
    Integer maxKP(Integer userId);

    Check findByUserIdAndKp(Integer userId, Integer kp);

    @Query("select ch " +
            "from Check as ch " +
            "where ch.kp = ?1 " +
            "order by ch.checkTime")
    List<Check> resultBrevet(Integer kp);
}
