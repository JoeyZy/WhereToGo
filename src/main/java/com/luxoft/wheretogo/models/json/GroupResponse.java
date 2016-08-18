package com.luxoft.wheretogo.models.json;

import com.luxoft.wheretogo.models.Group;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.utils.ImageUtils;
import lombok.Data;
import org.hibernate.Hibernate;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;

import static java.nio.file.Paths.get;

/**
 * Created by maks on 08.07.16.
 */
@Data
public class GroupResponse {
    private static final String EMPTY_PICTURE = "resources/images/noimg.png";

    private long id;
    private String name;
    private User owner;
    private String location;
    private String description;
    private String picture;
    private Boolean deleted;
    private Set<User> groupParticipants;

    public GroupResponse() {
        this.id=-1;
    }

    public GroupResponse(long id, String name, User owner, String location,
                         String description, String picture, Boolean deleted, Set<User> groupParticipants) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.location = location;
        this.description = description;
        this.groupParticipants = groupParticipants;
        if(!picture.equals("")){
            //this.picture = ImageUtils.giveMeImage(picture,false);
            this.picture = "groupImage?id="+id;
        }else{
            this.picture = picture;
        }
        this.deleted = deleted;
    }

}
