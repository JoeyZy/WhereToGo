package com.luxoft.wheretogo.models.json;

import lombok.Data;

/**
 * Created by maks on 08.07.16.
 */
@Data
public class GroupResponse {

    private long id;
    private String name;


    public GroupResponse(long id, String name) {
        this.id = id;
        this.name = name;
    }

}
