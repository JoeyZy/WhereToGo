package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.Comment;
import com.sun.istack.internal.logging.Logger;
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
        return super.findListByProperty("event", eventId);
    }
}
