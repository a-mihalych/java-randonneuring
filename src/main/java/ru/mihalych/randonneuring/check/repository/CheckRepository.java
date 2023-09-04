package ru.mihalych.randonneuring.check.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mihalych.randonneuring.check.model.Check;

public interface CheckRepository extends JpaRepository<Check, Integer> {

    @Query("select max(kp) " +
           "from Check " +
           "where user.id = ?1")
    Integer maxKP(Integer userId);

    @Query("select ch " +
           "from Check as ch " +
           "where ch.user.id = ?1 and " +
                "(ch.kp = ?2 or ch.kp = -?2)")
    Check userResult(Integer userId, Integer kp);
}
