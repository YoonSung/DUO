package tdd.duo.web.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tdd.duo.domain.Article;
import tdd.duo.domain.Comment;
import tdd.duo.service.CommentService;

/**
 * Created by yoon on 15. 4. 22..
 */
@Controller
@RequestMapping("/article/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //TODO Convert to Ajax Process
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void register(Comment comment) {

        commentService.create(comment);
    }
}
