package com.artlibrary.artlibrary.model;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "users")
public class User {
  @Id
  private String id;
  @NotBlank
  @Size(max = 20)
  private String username;
  @NotBlank
  @Size(max = 50)
  @Email
  private String email;
  @NotBlank
  @Size(max = 120)
  private String password;
  @NotBlank
  @Size(max = 40)
  private String firstname;
  @NotBlank
  @Size(max = 40)
  private String lastname;
  @DBRef
  private Set<Role> roles = new HashSet<>();

  public User() {
  }

  public User(String username, String email, String password, String firstname, String lastname, Set<Role> roles) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.firstname = firstname;
    this.lastname = lastname;
    this.roles = roles;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return this.lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public User id(String id) {
    this.id = id;
    return this;
  }

  public User username(String username) {
    this.username = username;
    return this;
  }

  public User email(String email) {
    this.email = email;
    return this;
  }

  public User password(String password) {
    this.password = password;
    return this;
  }

  public User firstname(String firstname) {
    this.firstname = firstname;
    return this;
  }

  public User lastname(String lastname) {
    this.lastname = lastname;
    return this;
  }

  public User roles(Set<Role> roles) {
    this.roles = roles;
    return this;
  }

  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", username='" + getUsername() + "'" +
      ", email='" + getEmail() + "'" +
      ", firstname='" + getFirstname() + "'" +
      ", lastname='" + getLastname() + "'" +
      ", roles='" + getRoles() + "'" +
      "}";
  }

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public Set<Role> getRoles() {
    return roles;
  }
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}