package tdd.duo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tdd.duo.domain.Article;
import tdd.duo.domain.User;
import tdd.duo.exception.ArticleCreationException;
import tdd.duo.service.ArticleService;
import tdd.duo.service.SessionService;

import java.net.UnknownServiceException;

/**
 * Created by yoon on 15. 4. 20..
 */
@Controller
public class GeneratorController {

    @Autowired
    ArticleService articleService;

    @Autowired
    SessionService sessionService;

    @RequestMapping("/generate")
    public @ResponseBody String generateMockData() throws ArticleCreationException {

        int maxNum = 100;

        User author = sessionService.getCurrentUser();

        for (int i = 1 ; i <=  maxNum; ++i) {
            articleService.create(new Article(author, "TestTitle_"+i, "TestContent_"+i));
        }

        return "success";
    }
}
