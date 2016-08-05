package com.luxoft.wheretogo.models.json;

import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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

    public GroupResponse() {
    }

    public GroupResponse(long id, String name, String owner, String location,
                         String description, String picture, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.location = location;
        this.description = description;
        if(!picture.equals("")){
            File img = new File(picture);
            try {
                FileReader reader = new FileReader(img);
                Scanner sc = new Scanner(img);
                picture = sc.nextLine();
                sc.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.picture = picture;
        this.deleted = deleted;
    }

}
