package com.luxoft.wheretogo.models.json;

import lombok.Data;

/**
 * Created by serhii on 05.08.16.
 */
@Data
public class CommentResponse {

    private long id;
    private String message;
    private String author;
    private long parentId;
    private String EventId;

    public CommentResponse() {
    }

    public CommentResponse(long id, String message, String author, long parentId,
                         String EventId) {
        this.id = id;
        this.message = message;
        this.author = author;
        this.parentId = parentId;
        this.EventId = EventId;
    }
}
