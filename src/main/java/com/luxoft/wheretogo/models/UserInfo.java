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
    private String role;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean active;

    public UserInfo(String role, String email, String firstName, String lastName, boolean active) {
        this.role = role;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
    }

    public UserInfo() {}
}
