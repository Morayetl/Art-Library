package com.artlibrary.artlibrary.requests;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginRequest {
    @NotNull 
    @NotEmpty
    public String username;

    @NotNull
    @NotEmpty
    public String password;


    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginRequest username(String username) {
        this.username = username;
        return this;
    }

    public LoginRequest password(String password) {
        this.password = password;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof LoginRequest)) {
            return false;
        }
        LoginRequest loginRequest = (LoginRequest) o;
        return Objects.equals(username, loginRequest.username) && Objects.equals(password, loginRequest.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "{" +
            " username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }

}