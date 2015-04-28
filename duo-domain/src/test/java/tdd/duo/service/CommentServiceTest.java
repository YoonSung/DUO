package tdd.duo.service;

import org.h2.engine.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import tdd.duo.config.DBConfig;
import tdd.duo.domain.Comment;
import tdd.duo.domain.User;
import tdd.duo.repository.CommentRepository;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

/**
 * Created by yoon on 15. 4. 22..
 */

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {DBConfig.class})
public class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    SessionService sessionService;

    @InjectMocks
    CommentService commentService;

    @Test
    public void registerWithEmptyContent() {

        when(sessionService.getCurrentUser()).thenReturn(new User());
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

    //TODO
    /*
    @Test
    public void registerWithInvalidArticleId() {
        when(sessionService.getCurrentUser()).thenReturn(new User());
        Comment comment = new Comment(null, "Test");

        try {
            commentService.create(comment);
            fail("Must not here!");
        } catch (IllegalArgumentException e) {
        }
    }
    */
}