package tdd.duo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdd.duo.domain.Article;
import tdd.duo.exception.ArticleCreationException;
import tdd.duo.repository.ArticleRepository;

/**
 * Created by yoon on 15. 4. 14..
 */
@Service
public class ArticleService {

    public static final String VALIDATION_EXCEPTION_MESSAGE = "입력데이터를 다시확인해 주시기 바랍니다.";

    @Autowired
    private ArticleRepository articleRepository;

    public void create(Article article) throws ArticleCreationException {

        //TODO 유저 로그인확인

        if (article.isRegisterable())
            articleRepository.save(article);
        else
            throw new ArticleCreationException(VALIDATION_EXCEPTION_MESSAGE);
    }
}
