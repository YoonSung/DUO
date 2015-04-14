package tdd.duo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tdd.duo.domain.Article;

/**
 * Created by yoon on 15. 4. 14..
 */
public interface BoardRepository extends JpaRepository<Article, Long> {
}
