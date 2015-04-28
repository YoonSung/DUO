package tdd.duo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import tdd.duo.config.DBConfig;
import tdd.duo.domain.Comment;
import tdd.duo.repository.CommentRepository;

import static org.junit.Assert.fail;

/**
 * Created by yoon on 15. 4. 22..
 */

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {DBConfig.class})
public class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentService commentService;

    @Test
    public void registerWithEmptyContent() {

        Comment comment = new Comment(1L, null);

        try {
            commentService.create(comment);
            fail("Must not here!");
        } catch (IllegalArgumentException e) {
        }

        comment = new Comment(1L, "");

        try {
            commentService.create(comment);
            fail("Must not here!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void registerWithInvalidArticleId() {

        Comment comment = new Comment(null, "Test");

        try {
            commentService.create(comment);
            fail("Must not here!");
        } catch (IllegalArgumentException e) {
        }
    }
}