package tdd.duo.web.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tdd.duo.domain.Article;
import tdd.duo.exception.ArticleCreationException;
import tdd.duo.exception.ArticleModificationException;
import tdd.duo.service.ArticleService;

import java.util.List;

/**
 * Created by yoon on 15. 4. 14..
 */
@Controller
@RequestMapping("/board")
public class BoardController {

    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

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
            //TODO Refactoring to key-value parameter
            model.addAttribute(article.getTitle());
            model.addAttribute(article.getContent());
            model.addAttribute(exception.getMessage());
            return "/board/form";
        }

        return "redirect:/board/list";
    }

    //TODO Use PathVariable
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String modify(@PathVariable int id, Article article, Model model) {
        article.setId(id);

        log.debug("request article {}", article.toString());

        try {
            Article modifiedArticle = articleService.modify(article);
            log.debug("modifiedArticle : {}", modifiedArticle.toString());
            return "redirect:/board/article/"+modifiedArticle.getId();

        } catch (ArticleModificationException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/board/article/"+article.getId();
    }
}
