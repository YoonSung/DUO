package tdd.duo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tdd.duo.domain.ViewCount;

/**
 * Created by yoon on 15. 4. 28..
 */
public interface ViewCountRepository extends JpaRepository<ViewCount, Long> {

    @Query(value = "SELECT count(o.id) FROM ViewCount o WHERE o.article_id = :article_id")
    public int getCount(@Param("article_id") Long id);
}