package com.luxoft.wheretogo.models.json;

import lombok.Data;

/**
 * Created by maks on 08.07.16.
 */
@Data
public class GroupResponse {

    private long id;
    private String name;
    private String owner;


    public GroupResponse(long id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

}
