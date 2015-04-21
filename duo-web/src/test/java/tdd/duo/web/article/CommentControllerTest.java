package tdd.duo.web.article;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import tdd.duo.config.DBConfig;
import tdd.duo.config.WebConfig;
import tdd.duo.domain.Comment;
import tdd.duo.service.CommentService;
import tdd.duo.web.MvcTestUtil;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by yoon on 15. 4. 22..
 */

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {DBConfig.class})
public class CommentControllerTest {


    @Mock
    CommentService commentService;

    @Mock
    CommentController commentController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MvcTestUtil.getMockMvc(commentController);
    }

    //TODO Refactoring (Ajax Process)
    @Test
    public void create() throws Exception {

        // - GIVEN
        Long articleId = 1L;
        String content = "testContent";
        Comment comment = new Comment(articleId, content);

        when(commentService.create(comment)).thenReturn(comment);

        // - WHEN, THEN
        mockMvc.perform(post("/article/comment")
                .param("articleId", articleId.toString())
                .param("content", content)
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article/"+articleId));
    }

    //TODO Interaction to user (errorMessage have to be shown)
    //TODO Create. (current version is useless)
    @Test
    public void createWithInvalidArticleId() throws Exception {
        fail("TODO");
    }

    //TODO Create. (current version is useless)
    @Test
    public void createWithInvalidCommentData() throws Exception {
        fail("TODO");
    }


}
