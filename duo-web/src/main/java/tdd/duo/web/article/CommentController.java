package tdd.duo.web.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tdd.duo.domain.Article;
import tdd.duo.domain.Comment;
import tdd.duo.service.CommentService;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

/**
 * Created by yoon on 15. 4. 22..
 */
@Controller
@RequestMapping("/article/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //TODO Convert to Ajax Process
    //TODO Try - Catch to detect exception and announce error to user
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String register(Comment comment, Model model) {
        try {
            commentService.create(comment);
        } catch (IllegalArgumentException e) {
            //TODO Unused model. convert to ajax and user them
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/article/"+comment.getArticleId();
    }
}
