package tdd.duo.web.article;

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
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //TODO Add model data(articles), Refactoring with listFromQuery
    @RequestMapping("")
    public String list() {
        return "/article/list";
    }

    @RequestMapping("/query")
    public String listFromQuery(String query, Model model) {

        List<Article> articles = articleService.findsByQueryString(query);
        model.addAttribute("articles", articles);

        return "/article/list";
    }

    @RequestMapping("/register")
    public String creationForm() {
        return "/article/register";
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
            return "/article/form";
        }

        return "redirect:/article/list";
    }

    //TODO Use PathVariable
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String modify(@PathVariable int id, Article article, Model model) {
        article.setId(id);

        try {
            Article modifiedArticle = articleService.modify(article);
            return "redirect:/article/"+modifiedArticle.getId();

        } catch (ArticleModificationException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/article/"+article.getId();
    }
}
