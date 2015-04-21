package tdd.duo.dto;

import org.springframework.data.domain.Page;
import tdd.duo.domain.Article;
import tdd.duo.service.ArticleService;

import java.util.List;

/**
 * Created by yoon on 15. 4. 21..
 */
public class ArticlePage {


    private Page<Article> page;

    private int current;
    private int start;
    private int end;
    private int totalEnd;

    private List<Article> articles;

    public ArticlePage(int start, int current, int end, int totalEnd, List<Article> list) {
        this.start = start;
        this.current = current;
        this.end = end;
        this.totalEnd = totalEnd;
        this.articles = list;
    }

    public List<Article> getArticles() {
        if (this.articles == null)
            this.articles = this.page.getContent();
        return articles;
    }

    public ArticlePage(Page<Article> page) {
        this.page = page;
    }

    public int getCurrentPage() {
        if (this.current == 0)
            this.current = this.page.getNumber() + 1;

        return this.current;
    }

    public int getStartPage() {
        if (this.start == 0)
            this.start = Math.max(1, getCurrentPage());

        return this.start;
    }

    public int getEndPage() {
        if (this.end == 0)
            this.end = Math.min(getCurrentPage() + ArticleService.PAGENATION_INTERVAL_FROM_CURRENT_PAGENUMBER, this.page.getTotalPages());

        return end;
    }

    public int getTotalEndPage() {

        if (this.totalEnd == 0)
            this.totalEnd = this.page.getTotalPages();
        return this.totalEnd;
    }
}
