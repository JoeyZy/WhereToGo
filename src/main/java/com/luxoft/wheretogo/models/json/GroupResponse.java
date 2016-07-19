package com.luxoft.wheretogo.models.json;

import lombok.Data;

/**
 * Created by maks on 08.07.16.
 */
@Data
public class GroupResponse {
    private static final String EMPTY_PICTURE = "resources/images/noimg.png";

    private long id;
    private String name;
    private String owner;
    private String location;
    private String description;
    private String picture;
    private Boolean deleted;

    public GroupResponse(long id, String name, String owner, String location,
                         String description, String picture, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.location = location;
        this.description = description;
        this.picture = picture;
        this.deleted = deleted;
    }

}
