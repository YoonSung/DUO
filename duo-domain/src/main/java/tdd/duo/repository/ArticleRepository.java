package tdd.duo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tdd.duo.domain.Article;

import java.util.List;

/**
 * Created by yoon on 15. 4. 14..
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article as a WHERE a.title LIKE '%:query%' OR a.content LIKE '%:query%'")
    List<Article> findsByQueryStringFromTitleAndContent(@Param("query") String query);
}