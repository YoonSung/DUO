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
    public static final String LOGINUSER_EXCEPTION_MESSAGE = "로그인하지 않은 유저의 새글등록 요청";

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private SessionService sessionService;

    public void create(Article article) throws ArticleCreationException {

        /* TODO 로그인유저를 가져와서, article author로 지정하는 로직이 필요
        //유저 로그인여부 확인. 유저데이터를 가져와서 author로 저장한다.
        if (sessionService.isLoginStatus()) {
            article.setAuthor(sessionService.getCurrentUser());
        } else {
            throw new ArticleCreationException(LOGINUSER_EXCEPTION_MESSAGE);
        }
        */

        //새로 업로드요청한 데이터의 정합성 체크
        if (article.isRegisterable())
            articleRepository.save(article);
        else
            throw new ArticleCreationException(VALIDATION_EXCEPTION_MESSAGE);
    }
}
