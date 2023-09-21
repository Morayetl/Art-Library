package com.artlibrary.artlibrary.responses;

import java.util.List;
import java.util.Objects;

public class UserResponse {
    public String id;
    public String username;
    public String email;
    public List<String> roles;
    public String jwt;

    public UserResponse() {
    }

    public UserResponse(String id, String username, String email, List<String> roles, String jwt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.jwt = jwt;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getJwt() {
        return this.jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserResponse id(String id) {
        this.id = id;
        return this;
    }

    public UserResponse username(String username) {
        this.username = username;
        return this;
    }

    public UserResponse email(String email) {
        this.email = email;
        return this;
    }

    public UserResponse roles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    public UserResponse jwt(String jwt) {
        this.jwt = jwt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserResponse)) {
            return false;
        }
        UserResponse userResponse = (UserResponse) o;
        return Objects.equals(id, userResponse.id) && Objects.equals(username, userResponse.username) && Objects.equals(email, userResponse.email) && Objects.equals(roles, userResponse.roles) && Objects.equals(jwt, userResponse.jwt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, roles, jwt);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", username='" + getUsername() + "'" +
            ", email='" + getEmail() + "'" +
            ", roles='" + getRoles() + "'" +
            ", jwt='" + getJwt() + "'" +
            "}";
    }
}
