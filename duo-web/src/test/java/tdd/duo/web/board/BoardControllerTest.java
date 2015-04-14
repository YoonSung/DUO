package tdd.duo.web.board;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import tdd.duo.config.DBConfig;
import tdd.duo.config.WebConfig;
import tdd.duo.domain.Article;
import tdd.duo.domain.User;
import tdd.duo.service.ArticleService;
import tdd.duo.web.MvcTestUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by yoon on 15. 4. 14..
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {DBConfig.class})
public class BoardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private BoardController boardController;

    @Before
    public void setUp() {
        this.mockMvc = MvcTestUtil.getMockMvc(boardController);
    }

    @Test
    public void listViewRequest() throws Exception {

        String expectedUrl = "/board/list";

        mockMvc.perform(get("/board"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedUrl))
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + expectedUrl + WebConfig.RESOLVER_SUFFIX));
    }

    @Test
    public void creationViewRequest() throws Exception {

        String expectedUrl = "/board/register";

        mockMvc.perform(get("/board/register"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedUrl))
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + expectedUrl + WebConfig.RESOLVER_SUFFIX));
    }

    @Test
    public void createNewArticle() throws Exception {
        //TODO 첨부파일 업로드
        String title = "TITLE";
        String content = "CONTENT";

        mockMvc.perform(post("/board")
                .param("title", title)
                .param("content", content)
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/board/list"));
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
        ResultActions resultActions = mockMvc.perform(get("/board/query")
                        .param("query", queryString)
        )
        //THEN
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + "/board/list" + WebConfig.RESOLVER_SUFFIX))
                .andExpect(model().attributeExists("articles"));


        List<Article> returnArticles = (List<Article>) resultActions.andReturn().getModelAndView().getModel().get("articles");
        assertEquals(querySelectedArticles, returnArticles);
    }
}
