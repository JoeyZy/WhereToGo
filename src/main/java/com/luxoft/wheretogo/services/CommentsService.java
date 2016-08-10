package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Comment;

import java.util.List;

/**
 * Created by serhii on 05.08.16.
 */
public interface CommentsService {
    void add(Comment comment);

    void update(Comment comment);

    Comment findById(long commentId);

    List<Comment> findByEventId(long eventId);
}
