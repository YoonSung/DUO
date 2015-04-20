package tdd.duo.web.article;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import tdd.duo.config.DBConfig;
import tdd.duo.config.WebConfig;
import tdd.duo.domain.Article;
import tdd.duo.domain.User;
import tdd.duo.exception.ArticleCreationException;
import tdd.duo.exception.ArticleModificationException;
import tdd.duo.exception.ArticleNotFoundException;
import tdd.duo.service.ArticleService;
import tdd.duo.web.MvcTestUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by yoon on 15. 4. 14..
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {DBConfig.class})
public class ArticleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    @Before
    public void setUp() {
        this.mockMvc = MvcTestUtil.getMockMvc(articleController);
    }

    @Test
    public void listViewRequest() throws Exception {

        String expectedUrl = "/article/list";

        mockMvc.perform(get("/article/list"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedUrl))
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + expectedUrl + WebConfig.RESOLVER_SUFFIX));
    }

    @Test
    public void creationViewRequest() throws Exception {

        String expectedUrl = "/article/register";

        mockMvc.perform(get("/article/register"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedUrl))
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + expectedUrl + WebConfig.RESOLVER_SUFFIX));
    }

    @Test
    public void createNewArticle() throws Exception {
        //TODO 첨부파일 업로드
        String title = "TITLE";
        String content = "CONTENT";

        mockMvc.perform(post("/article")
                        .param("title", title)
                        .param("content", content)
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article/list"));
    }

    @Test
    public void createNewArticleWithWrongData() throws Exception {
        String title = "";
        String content = "";

        String expectedUrl = "/article/register";

        Mockito.doThrow(new ArticleCreationException(ArticleService.VALIDATION_EXCEPTION_MESSAGE)).when(articleService).create(any());

        MvcResult mvcResult = mockMvc.perform(post("/article")
                        .param("title", title)
                        .param("content", content)
        )
                .andExpect(status().isOk())
                .andExpect(view().name(expectedUrl))
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + expectedUrl + WebConfig.RESOLVER_SUFFIX))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().size(2)).andReturn();

        String errorMessage = (String) mvcResult.getModelAndView().getModel().get("errorMessage");

        assertEquals(errorMessage, ArticleService.VALIDATION_EXCEPTION_MESSAGE);
    }

    @Test
    public void getArticlesFromQueryString() throws Exception {

        //GIVEN
        String queryString = "test";
        int expectedResultSize = 10;

        String testTitle = "testTitle";
        String testContent = "testContent";
        List<Article> querySelectedArticles = new ArrayList<Article>();

        for (int i = 1 ; i <= expectedResultSize ; ++i) {
            Article article = new Article(new User(), testTitle+i, testContent+i);
            querySelectedArticles.add(article);
        }

        when(articleService.findsByQueryString(queryString)).thenReturn(querySelectedArticles);

        //WHEN
        ResultActions resultActions = mockMvc.perform(get("/article/query")
                        .param("query", queryString)
        )
        //THEN
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + "/article/list" + WebConfig.RESOLVER_SUFFIX))
                .andExpect(model().attributeExists("articles"));


        List<Article> returnArticles = (List<Article>) resultActions.andReturn().getModelAndView().getModel().get("articles");
        assertEquals(querySelectedArticles, returnArticles);
    }

    @Test
    public void modifyArticle() throws Exception {

        //GIVEN
        String testTitle = "testTitle";
        String testContent = "testContent";

        Article requestArticle = new Article(null, testTitle, testContent);
        requestArticle.setId(1);

        when(articleService.modify(any())).thenReturn(requestArticle);

        //WHEN, THEN
        mockMvc.perform(put("/article/"+requestArticle.getId())
                        .param("title", testTitle)
                        .param("content", testContent)
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article/" + requestArticle.getId()));
    }

    @Test
    public void modifyArticleWithWrongData() throws Exception {

        //GIVEN
        String testTitle = "";
        String testContent = "";
        int articleId = 1;

        when(articleService.modify(any())).thenThrow(new ArticleModificationException(ArticleService.VALIDATION_EXCEPTION_MESSAGE));

        String expectedUrl = "/article/register";
        //WHEN, THEN
        MvcResult mvcResult = mockMvc.perform(put("/article/" + articleId)
                        .param("title", testTitle)
                        .param("content", testContent)
        )
                .andExpect(status().isOk())
                .andExpect(view().name(expectedUrl))
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + expectedUrl + WebConfig.RESOLVER_SUFFIX))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("article", "errorMessage")).andReturn();

        String errorMessage = (String) mvcResult.getModelAndView().getModel().get("errorMessage");
        assertEquals(errorMessage, ArticleService.VALIDATION_EXCEPTION_MESSAGE);
    }

    @Test
    public void deleteArticle() throws Exception {

        Long articleId = 1L;

        mockMvc.perform(delete("/article/"+articleId))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article"));
    }

    @Test
    public void deleteArticleWithWrongArticleNumber() throws Exception {

        int articleId = -1;
        mockMvc.perform(delete("/article/"+articleId))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article"));
    }

    @Test
    public void deleteArticleWithDidNotExistArticle() throws Exception {

        Long articleId = 1L;

        Mockito.doThrow(new ArticleNotFoundException()).when(articleService).delete(articleId);

        mockMvc.perform(delete("/article/"+articleId))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article"));
    }
}