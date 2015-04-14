package tdd.duo.web.board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sun.plugin2.main.client.MacOSXMozillaServiceDelegate;
import tdd.duo.domain.Article;

/**
 * Created by yoon on 15. 4. 14..
 */
@Controller
@RequestMapping("/board")
public class BoardController {

    private ArticleService articleService;

    @RequestMapping("")
    public String list() {
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
