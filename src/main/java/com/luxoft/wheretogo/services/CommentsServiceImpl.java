package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Comment;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.repositories.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Iterator;
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
    public void update(Comment comment, User user) {
        Comment oldComment = findById(comment.getId());
        if (oldComment != null) {
            comment.setCreated(oldComment.getCreated());
            comment.setAuthor(oldComment.getAuthor());
            comment.setParent(oldComment.getParent());
            if(comment.getDeleted() == null){
                comment.setDeleted(false);
            }
        }
        if(comment.getAuthor().getId() != user.getId()){
            return;
        }
        comment.setModified(new Date());
        commentsRepository.merge(comment);
        comment.setModified(findById(comment.getId()).getModified());
    }

    public List<Comment> findByEventId(long eventId) {
        List<Comment> commentList = commentsRepository.findByEventId(eventId);
        Iterator<Comment> i = commentList.iterator();
        while(i.hasNext()){
            if(i.next().getDeleted() == true){
                i.remove();
            }
        }
        return commentList;
    }
    @Override
    public Comment findById(long commentId) {
        return commentsRepository.findById(commentId);
    }
}
