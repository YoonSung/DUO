package tdd.duo.web.article;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import tdd.duo.config.DBConfig;
import tdd.duo.web.MvcTestUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yoon on 15. 4. 22..
 */

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {DBConfig.class})
public class CommentControllerTest {

    @Mock
    CommentController commentController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MvcTestUtil.getMockMvc(commentController);
    }

    @Test
    public void create() throws Exception {

        Long articleId = 1L;
        String content = "testContent";
        String expectedUrl = "/article/"+articleId;

        mockMvc.perform(post("/article/comment")
                .param("articleId", articleId.toString())
                .param("content", content)
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article/"+articleId));
    }

}
