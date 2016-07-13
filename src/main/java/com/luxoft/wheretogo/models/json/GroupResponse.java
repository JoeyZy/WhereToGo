package com.luxoft.wheretogo.models.json;

import lombok.Data;

/**
 * Created by maks on 08.07.16.
 */
@Data
public class GroupResponse {

    private long id;
    private String name;
    private String location;
    private String description;
    private String picture;


    public GroupResponse(long id, String name,String location,String description, String picture) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.picture = picture;
    }

}
