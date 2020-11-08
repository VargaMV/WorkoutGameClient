package com.msh.WorkoutGameClient.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginUser {
    private String username;
    private String password;

    public LoginUser() {
    }

    public LoginUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
