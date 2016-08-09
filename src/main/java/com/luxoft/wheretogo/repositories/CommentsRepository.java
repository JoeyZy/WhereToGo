package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.Comment;

import java.util.List;

/**
 * Created by serhii on 05.08.16.
 */
public interface CommentsRepository {
    void add(Comment comment);

    List<Comment> findByEventId(long eventId);
}
