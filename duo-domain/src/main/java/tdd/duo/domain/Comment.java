package tdd.duo.domain;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yoon on 15. 4. 22..
 */
@Entity
@Table
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column(nullable = false)
    private String content;

    @Column(name = "article_id", nullable = false)
    private Long articleId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    public Comment() {}

    public Comment(Long articleId, String content) {
        this.articleId = articleId;
        this.content = content;
    }

    public boolean canRegistable() {
        if (StringUtils.isEmpty(content))
            return false;

        if (this.author == null)
            return false;

        return true;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getArticleId() {
        return articleId;
    }
}