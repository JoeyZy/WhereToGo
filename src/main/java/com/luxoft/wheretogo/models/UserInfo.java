package com.luxoft.wheretogo.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by sasha on 19.07.16.
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"name", "email"})
@ToString(of = {"name", "email"})
public class UserInfo {
    private long id;
    private String role;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String picture;
    private boolean active;

    public UserInfo(String role, String email, String firstName, String lastName, boolean active, String picture, long id) {
        this.id=id;
        this.role = role;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        if(!picture.equals("")){
            this.picture = "userImage";
        }else{
            this.picture = picture;
        }
    }

    public UserInfo() {}
}
