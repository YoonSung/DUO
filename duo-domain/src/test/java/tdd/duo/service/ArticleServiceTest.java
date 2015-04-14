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
import tdd.duo.exception.ArticleModificationException;
import tdd.duo.repository.ArticleRepository;

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
    private ArticleRepository articleRepository;

    @Mock
    private SessionService sessionService;

    @InjectMocks
    private ArticleService articleService;

    @Test(expected = ArticleCreationException.class)
     public void 잘못된데이터로_새글쓰기를_요청한경우() throws ArticleCreationException {

        // GIVEN
        Article article = new Article(new User(), "", "");
        ArticleService articleService = new ArticleService();

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


    @Test(expected = ArticleModificationException.class)
    public void 잘못된_사용자가_글수정을_요청() throws ArticleModificationException {

        //GIVEN
        User modifyRequestArticleAuthor = new User();
        modifyRequestArticleAuthor.setId(1L);
        Article modifyRequestArticle = new Article(modifyRequestArticleAuthor, "testestTitle", "testestContent");

        User currentUser = new User();
        currentUser.setId(2L);
        when(sessionService.getCurrentUser()).thenReturn(currentUser);

        //WHEN
        articleService.modify(modifyRequestArticle);

        // THEN
        thrown.expect(ArticleCreationException.class);
        thrown.expectMessage(ArticleService.INVALID_REQUEST_EXCEPTION_MESSAGE);
    }

    @Test(expected = ArticleModificationException.class)
    public void 존재하지않는_글을_수정해달라고_요청() throws ArticleModificationException {
        //GIVEN
        User modifyRequestArticleAuthor = new User();
        modifyRequestArticleAuthor.setId(1L);
        Article modifyRequestArticle = new Article(modifyRequestArticleAuthor, "testestTitle", "testestContent");
        modifyRequestArticle.setId(1L);

        when(articleRepository.findOne(modifyRequestArticle.getId())).thenReturn(null);

        //WHEN
        articleService.modify(modifyRequestArticle);

        // THEN
        thrown.expect(ArticleCreationException.class);
        thrown.expectMessage(ArticleService.INVALID_REQUEST_EXCEPTION_MESSAGE);
    }

    @Test(expected = ArticleModificationException.class)
    public void 잘못된데이터로_글수정을_요청() throws ArticleModificationException {
        //GIVEN
        User modifyRequestArticleAuthor = new User();
        modifyRequestArticleAuthor.setId(1L);
        Article modifyRequestArticle = new Article(modifyRequestArticleAuthor, null, "");
        modifyRequestArticle.setId(1L);
        modifyRequestArticle.setAuthor(modifyRequestArticleAuthor);

        when(articleRepository.findOne(modifyRequestArticle.getId())).thenReturn(modifyRequestArticle);
        when(sessionService.getCurrentUser()).thenReturn(modifyRequestArticleAuthor);

        //WHEN
        articleService.modify(modifyRequestArticle);

        // THEN
        thrown.expect(ArticleCreationException.class);
        thrown.expectMessage(ArticleService.VALIDATION_EXCEPTION_MESSAGE);
    }
}
