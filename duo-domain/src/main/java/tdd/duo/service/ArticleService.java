package tdd.duo.service;

import org.h2.engine.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tdd.duo.domain.Article;
import tdd.duo.domain.User;
import tdd.duo.exception.ArticleCreationException;
import tdd.duo.exception.ArticleModificationException;
import tdd.duo.repository.ArticleRepository;

import java.net.UnknownServiceException;
import java.util.List;

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

    public List<Article> findsByQueryString(String query) {

        if (StringUtils.isEmpty(query))
            return null;

        return articleRepository.findsByQueryStringFromTitleAndContent(query);
    }

    public Article modify(Article requestArticle) throws ArticleModificationException {

        User user = sessionService.getCurrentUser();
        requestArticle.setAuthor(user);

        //수정요청 데이터의 정합성 확인
        if (!requestArticle.isRegisterable()) {
            throw new ArticleModificationException(VALIDATION_EXCEPTION_MESSAGE);
        }

        Article article = articleRepository.findOne(requestArticle.getId());

        //수정요청에 해당하는 기존의 article이 있는지 확인
        if (article == null) {
            throw new ArticleModificationException("잘못된 요청입니다");
        }

        //기존의 article author와 현재로그인한 user가 같은지 확인
        if (article.getAuthor().getId() != user.getId()) {
            throw new ArticleModificationException("잘못된 요청입니다");
        }

        return articleRepository.save(requestArticle);
    }
}
