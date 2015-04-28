package tdd.duo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tdd.duo.domain.Comment;

/**
 * Created by yoon on 15. 4. 22..
 */
public interface CommentRepository extends JpaRepository<Comment, Long>{
}
