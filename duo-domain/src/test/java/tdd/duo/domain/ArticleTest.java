package tdd.duo.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by yoon on 15. 4. 14..
 */
public class ArticleTest {

    private Article article;

    @Before
    public void setUp() {
        this.article = new Article(new User(), "testTitle", "testContent");
    }

    //Validation Test
    @Test
    public void 글작성자가_없을때_등록요청() {
        article.setAuthor(null);
        assertFalse(article.isRegisterable());
    }

    @Test
    public void 글제목이_null이거나_빈문자열일때_등록요청() {
        article.setTitle("");
        assertFalse(article.isRegisterable());

        article.setTitle(null);
        assertFalse(article.isRegisterable());
    }

    @Test
    public void 글내용이_null이거나_빈문자열일때_등록요청() {
        article.setContent("");
        assertFalse(article.isRegisterable());

        article.setContent(null);
        assertFalse(article.isRegisterable());
    }
}
