package com.artlibrary.artlibrary.model;
import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "views")
public class View {
  @Id
  private String id;
  private String media;
  private Date created;

  public View() {
  }

  public View(String id, String media, Date created) {
    this.id = id;
    this.media = media;
    this.created = created;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMedia() {
    return this.media;
  }

  public void setMedia(String media) {
    this.media = media;
  }

  public Date getCreated() {
    return this.created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public View id(String id) {
    this.id = id;
    return this;
  }

  public View media(String media) {
    this.media = media;
    return this;
  }

  public View created(Date created) {
    this.created = created;
    return this;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof View)) {
            return false;
        }
        View view = (View) o;
        return Objects.equals(id, view.id) && Objects.equals(media, view.media) && Objects.equals(created, view.created);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, media, created);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", media='" + getMedia() + "'" +
      ", created='" + getCreated() + "'" +
      "}";
  }
}