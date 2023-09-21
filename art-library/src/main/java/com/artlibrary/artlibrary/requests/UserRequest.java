package com.artlibrary.artlibrary.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserRequest {
    @NotNull
    @NotEmpty
    @Min(2)
    public String firstname;
    
    @NotNull
    @NotEmpty
    @Min(2)
    public String lastname;
    
    @NotNull
    @NotEmpty
    public String username;
    
    @NotNull
    @NotEmpty
    public String password;
    
    public String matchingPassword;

    @NotNull
    @NotEmpty
    public String email;

    public UserRequest() {
    }

    public UserRequest(String firstname, String lastname, String username, String password, String matchingPassword, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
    }

    public String getFirstName() {
        return this.firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return this.lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getUserName() {
        return this.username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRequest firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public UserRequest lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public UserRequest username(String username) {
        this.username = username;
        return this;
    }

    public UserRequest password(String password) {
        this.password = password;
        return this;
    }

    public UserRequest matchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
        return this;
    }

    public UserRequest email(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " firstname='" + getFirstName() + "'" +
            ", lastname='" + getLastName() + "'" +
            ", username='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", matchingPassword='" + getMatchingPassword() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

}