package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.Comment;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by serhii on 05.08.16.
 */
@Repository
public class CommentsRepositoryImpl extends AbstractRepository<Comment> implements CommentsRepository{
    private final static Logger LOGGER = Logger.getLogger(CommentsRepositoryImpl.class);
    public CommentsRepositoryImpl() {
        super(Comment.class);
    }

    @Override
    public List<Comment> findByEventId(long eventId) {
        return super.findListByProperty("eventId", eventId);
    }

    @Override
    public List<Comment> findByGroupId(long groupId) {
        return super.findListByProperty("groupId", groupId);
    }

    @Override
    public void add(Comment comment) {
        super.add(comment);
    }

    @Override
    public Comment findById(long commentId){ return super.findByProperty("id", commentId); }

    @Override
    public void merge(Comment comment) {
        super.merge(comment);
    }
}
