package tdd.duo.domain;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yoon on 15. 4. 14..
 */
@Entity
@Table
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    public Article(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isRegisterable() {
        if (isValidAuthor() == true && isValidTitle() == true && isValidContent() == true)
            return true;

        return false;
    }

    private boolean isValidContent() {
        return isValidString(this.content) ? true : false;
    }

    private boolean isValidTitle() {
        return isValidString(this.title) ? true : false;
    }

    private boolean isValidAuthor() {
        return this.author != null ? true : false;
    }

    private boolean isValidString(String string) {
        if (StringUtils.isEmpty(string))
            return false;
        return true;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
