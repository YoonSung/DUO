package tdd.duo.domain;

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

    @Column
    private User author;

    @Column
    private String content;

    @Column(name = "article_id")
    private Long articleId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    public Comment() {}

    public Comment(Long articleId, String content) {
        this.articleId = articleId;
        this.content = content;
    }
}