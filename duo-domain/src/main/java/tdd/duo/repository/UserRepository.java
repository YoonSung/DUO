package tdd.duo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tdd.duo.domain.User;

/**
 * Created by yoon on 15. 3. 25..
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
