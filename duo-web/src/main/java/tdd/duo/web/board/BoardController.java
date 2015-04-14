package tdd.duo.web.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tdd.duo.domain.Article;
import tdd.duo.exception.ArticleCreationException;
import tdd.duo.service.ArticleService;

import java.util.List;

/**
 * Created by yoon on 15. 4. 14..
 */
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private ArticleService articleService;

    //TODO Add model data(articles), Refactoring with listFromQuery
    @RequestMapping("")
    public String list() {
        return "/board/list";
    }

    @RequestMapping("/query")
    public String listFromQuery(String query, Model model) {

        List<Article> articles = articleService.findsByQueryString(query);
        model.addAttribute("articles", articles);

        return "/board/list";
    }

    @RequestMapping("/register")
    public String creationForm() {
        return "/board/register";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String create(Article article, Model model) {

        try {
            articleService.create(article);
        } catch (ArticleCreationException exception) {
            model.addAttribute(article.getTitle());
            model.addAttribute(article.getContent());
            model.addAttribute(exception.getMessage());
            return "/board/form";
        }

        return "redirect:/board/list";
    }
}
