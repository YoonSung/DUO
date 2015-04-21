package tdd.duo.web.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tdd.duo.domain.Article;
import tdd.duo.dto.ArticlePage;
import tdd.duo.exception.ArticleCreationException;
import tdd.duo.exception.ArticleModificationException;
import tdd.duo.exception.ArticleNotFoundException;
import tdd.duo.service.ArticleService;

import javax.naming.AuthenticationException;
import java.util.List;

/**
 * Created by yoon on 15. 4. 14..
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/list")
    public String pageList(Integer page, Model model) {

        if (page == null) {
            page = 1;

        } else if (page <= 0) {
            page = 1;
            model.addAttribute("errorMessage", "잘못된 페이지 요청입니다.");
        }

        ArticlePage articlePage;

        try {
            articlePage = articleService.findsByPageNumber(page);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "잘못된 페이지 요청입니다.");
            articlePage = articleService.findsByPageNumber(1);
        }

        model.addAttribute("startPage", articlePage.getStartPage());
        model.addAttribute("currentPage", articlePage.getCurrentPage());
        model.addAttribute("endPage", articlePage.getEndPage());
        model.addAttribute("totalEndPage", articlePage.getTotalEndPage());
        model.addAttribute("articles", articlePage.getArticles());

        return "/article/list";
    }

    //TODO Refactoring or delete
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
            model.addAttribute("article", article);
            model.addAttribute("errorMessage", exception.getMessage());
            return "/article/register";
        }

        return "redirect:/article/list";
    }

    @RequestMapping(value = "/{id}")
    public String detailView(@PathVariable Long id, Model model) {

        Article article = articleService.findById(id);

        if (article == null) {
            model.addAttribute("errorMessage", "잘못된 접근입니다");
            return "/article/list";
        }

        model.addAttribute("article", article);
        return "/article/detail";
    }

    //TODO Use PathVariable
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String modify(@PathVariable int id, Article article, Model model) {
        article.setId(id);

        try {
            Article modifiedArticle = articleService.modify(article);
            return "redirect:/article/"+modifiedArticle.getId();

        } catch (ArticleModificationException e) {
            model.addAttribute("article", article);
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "/article/register";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id) {

        try {
            articleService.delete(id);

        //TODO ArticleNotFoundException를 통해 이전의 다른 컨트롤러 에러처리도 리팩토링하자
        } catch (AuthenticationException e) {
            return "redirect:/article/"+id;
        } catch (ArticleNotFoundException e) {
            return "redirect:/article";
        }

        return "redirect:/article";
    }
}
