package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Comment;
import com.luxoft.wheretogo.repositories.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by serhii on 05.08.16.
 */
@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public void add(Comment comment) {
        commentsRepository.add(comment);
    }

    @Override
    public void update(Comment comment) {
        Comment oldComment = findById(comment.getId());
        if (oldComment != null) {
            comment.setCreated(oldComment.getCreated());
            comment.setAuthor(oldComment.getAuthor());
            comment.setParent(oldComment.getParent());
        }
        comment.setModified(new Date());
        commentsRepository.merge(comment);
    }

    public List<Comment> findByEventId(long eventId) {
        return commentsRepository.findByEventId(eventId);
    }
    @Override
    public Comment findById(long commentId) {
        return commentsRepository.findById(commentId);
    }
}
