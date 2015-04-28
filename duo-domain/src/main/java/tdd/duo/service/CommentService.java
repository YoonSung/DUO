package tdd.duo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tdd.duo.domain.Comment;
import tdd.duo.repository.CommentRepository;

/**
 * Created by yoon on 15. 4. 22..
 */
@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public Comment create(Comment comment) {

        Assert.notNull(comment);

        if (!comment.canRegistable())
            throw new IllegalArgumentException();

        return commentRepository.save(comment);
    }
}