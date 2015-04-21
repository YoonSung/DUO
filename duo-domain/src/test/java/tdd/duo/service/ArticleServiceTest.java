package tdd.duo.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import tdd.duo.config.DBConfig;
import tdd.duo.domain.Article;
import tdd.duo.domain.User;
import tdd.duo.exception.ArticleCreationException;
import tdd.duo.exception.ArticleModificationException;
import tdd.duo.exception.ArticleNotFoundException;
import tdd.duo.repository.ArticleRepository;

import javax.naming.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
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


    @Test
    public void 정상적인_리스트페이지_요청() {

        // - GIVEN
        int totalSelectedListNum = 500;
        int requestPageNumber = 13;

        List<Article> resultList = new ArrayList<Article>();

        for (int i = 1; i <= ArticleService.PAGE_PER_ARTICLE_NUMBER; i++) {
            resultList.add(new Article());
        }

        when(articleRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl(resultList, articleService.getPageRequest(requestPageNumber), totalSelectedListNum));

        // - WHEN
        Page<Article> pageResult = articleService.findsByPageNumber(requestPageNumber);


        // - THEN
        int current = pageResult.getNumber() + 1;
        int begin = Math.max(1, current - ArticleService.PAGENATION_INTERVAL_FROM_CURRENT_PAGENUMBER);
        int end = Math.min(current + ArticleService.PAGENATION_INTERVAL_FROM_CURRENT_PAGENUMBER, pageResult.getTotalPages());


        //content 갯수 확인 - 이건 어떻게 확인하지..?
        assertEquals(ArticleService.PAGE_PER_ARTICLE_NUMBER, pageResult.getContent().size());

        //return된 현재 페이지번호 확인
        assertEquals(requestPageNumber, current);

        //Pagination의 첫번째 노출페이지 번호 확인
        assertEquals(requestPageNumber - ArticleService.PAGENATION_INTERVAL_FROM_CURRENT_PAGENUMBER, begin);
        assertEquals(requestPageNumber + ArticleService.PAGENATION_INTERVAL_FROM_CURRENT_PAGENUMBER, end);
    }


    @Test(expected = ArticleCreationException.class)
     public void 잘못된데이터로_새글쓰기를_요청한경우() throws ArticleCreationException {

        // GIVEN
        Article article = new Article(new User(), "", "");
        when(sessionService.getCurrentUser()).thenReturn(new User());

        // WHEN
        articleService.create(article);

        // THEN
        thrown.expect(ArticleCreationException.class);
        thrown.expectMessage(ArticleService.VALIDATION_EXCEPTION_MESSAGE);
    }

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

    @Test(expected = AuthenticationException.class)
    public void 잘못된_사용자가_글삭제를_요청() throws ArticleNotFoundException, AuthenticationException {
        //GIVEN
        Long articleId = 1L;
        User requestUser = new User();
        requestUser.setId(1L);

        User author = new User();
        author.setId(2L);

        when(sessionService.getCurrentUser()).thenReturn(requestUser);
        when(articleRepository.findOne(articleId)).thenReturn(new Article(author, "testTitle", "testContent"));

        //WHEN, THEN
        articleService.delete(articleId);
    }

    @Test(expected = ArticleNotFoundException.class)
    public void 잘못된_글번호로_삭제를_요청() throws ArticleNotFoundException, AuthenticationException {
        Long articleId = -1L;
        articleService.delete(articleId);
    }

    @Test(expected = ArticleNotFoundException.class)
    public void 존재하지_않는_글에_대한_삭제를_요청() throws ArticleNotFoundException, AuthenticationException {
        Long articleId = 1L;

        when(articleRepository.findOne(articleId)).thenReturn(null);
        articleService.delete(articleId);
    }
}
