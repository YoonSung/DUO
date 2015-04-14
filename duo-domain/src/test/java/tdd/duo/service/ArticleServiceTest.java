package tdd.duo.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import tdd.duo.config.DBConfig;
import tdd.duo.domain.Article;
import tdd.duo.domain.User;
import tdd.duo.exception.ArticleCreationException;

import static org.mockito.Mockito.when;

/**
 * Created by yoon on 15. 4. 14..
 */

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {DBConfig.class})
public class ArticleServiceTest {

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Mock
    private ArticleService articleService;

    @Test(expected = ArticleCreationException.class)
     public void 잘못된데이터로_새글쓰기를_요청한경우() throws ArticleCreationException {

        // GIVEN
        Article article = new Article(new User(), "", "");
        Mockito.doCallRealMethod().when(articleService).create(article);

        // WHEN
        articleService.create(article);

        // THEN
        thrown.expect(ArticleCreationException.class);
        thrown.expectMessage(ArticleService.VALIDATION_EXCEPTION_MESSAGE);
    }

    //TODO SessionService를 완성해야 한다.
    //@Test(expected = ArticleCreationException.class)
    //public void 로그인_하지_않은_유저가_새로운글을_등록하려할때() throws ArticleCreationException {
    //}
}
