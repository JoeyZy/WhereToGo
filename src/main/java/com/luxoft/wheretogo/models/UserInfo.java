package com.luxoft.wheretogo.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

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
    private String description;
    private String phoneNumber;
    private boolean active;
    private long[] interestingCategories;

    public UserInfo(String role, String email, String firstName, String lastName, boolean active, String picture, long id, String description, String phone) {
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
        this.description=description;
        this.phoneNumber=phone;
    }

    public UserInfo() {}
}
